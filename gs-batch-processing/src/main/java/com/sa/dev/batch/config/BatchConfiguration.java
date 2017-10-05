package com.sa.dev.batch.config;

import com.sa.dev.batch.command.AppCommand;
import com.sa.dev.batch.entity.OverstaptabelRow;
import com.sa.dev.batch.entity.Person;
import com.sa.dev.batch.json.*;
import com.sa.dev.batch.listener.JobCompletionNotificationListener;
import com.sa.dev.batch.listener.ProcessStepListener;
import com.sa.dev.batch.listener.ReadStepListener;
import com.sa.dev.batch.mapper.OverstaptabelHeaderFieldSetMapper;
import com.sa.dev.batch.mapper.OverstaptabelRecordFieldSetMapper;
import com.sa.dev.batch.mapper.OverstaptabelTrailerFieldSetMapper;
import com.sa.dev.batch.partition.MultiFileResourcePartitioner;
import com.sa.dev.batch.partition.RangePartitioner;
import com.sa.dev.batch.processor.OverstaptabelRowProcessor;
import com.sa.dev.batch.processor.PersonItemProcessor;
import com.sa.dev.batch.processor.PersonProcessor;
import com.sa.dev.batch.skip.PersonVerificationSkipper;
import com.sa.dev.batch.token.OverstaptabelHeaderTokenizer;
import com.sa.dev.batch.token.OverstaptabelRecordTokenizer;
import com.sa.dev.batch.token.OverstaptabelTrailerTokenizer;
import com.sa.dev.batch.writer.OverstaptabelItemWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
@Slf4j
public class BatchConfiguration {


    @Autowired
    ResourcePatternResolver resoursePatternResolver;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;


    @Bean(name = "defaultReader")
    public FlatFileItemReader<Person> reader() {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        reader.setResource(new ClassPathResource("file/sample-data.csv"));
        setFieldMapper(reader);
        return reader;
    }

    @Bean
    public PersonItemProcessor processor() {
        return new PersonItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer() {
        log.info("in writer ");
        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
        writer.setDataSource(dataSource);
        return writer;
    }


    @Bean
    public SkipPolicy personVerificationSkipper() {
        return new PersonVerificationSkipper();
    }

    @Bean
    public ItemReadListener getItemReadListener() {
        return new ReadStepListener();
    }

    @Bean
    public ItemProcessListener getItemProcessListener() {
        return new ProcessStepListener();
    }


    @Bean(name = "defaultStep")
    public Step step1() {
        return stepBuilderFactory.get("defaultStep")
                .<Person, Person>chunk(2)
                .reader(reader())
                .processor(processor())
                .faultTolerant().skipPolicy(personVerificationSkipper())
                .writer(writer())
                .listener(getItemReadListener())
                .listener(getItemProcessListener())
                .build();
    }


    @Bean(name = "defaultJob")
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("defaultJob")
                .incrementer(new JobParametersIncrementer() {
                    @Override
                    public JobParameters getNext(JobParameters jobParameters) {
                        JobParametersBuilder builder = new JobParametersBuilder();
                        builder.addDate("date", new Date());
                        return builder.toJobParameters();
                    }
                })
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }


    @Bean(name = "customUserReader")
    @StepScope
    public FlatFileItemReader<Person> customUserReader(@Value("#{jobParameters['fileName']}") String inputPath) throws Exception {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        File file = new File(inputPath);
        reader.setResource(new UrlResource(file.toURI()));
        setFieldMapper(reader);
        return reader;
    }


    private void setFieldMapper(FlatFileItemReader reader) {
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"firstName", "lastName"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
    }

    @Bean(name = "customUserStep")
    public Step customUserStep(@Qualifier("customUserReader") FlatFileItemReader<Person> reader, JdbcBatchItemWriter<Person> itemWriter) {
        return stepBuilderFactory.get("customUserStep")
                .<Person, Person>chunk(10)
                .reader(reader)
                .processor(processor())
                .writer(itemWriter)
                .build();
    }

    @Bean(name = "customUserJob")
    public Job customUserJob(JobCompletionNotificationListener listener, @Qualifier("customUserStep") Step step) {
        return jobBuilderFactory.get("customUserJob")
                .incrementer(new JobParametersIncrementer() {
                    @Override
                    public JobParameters getNext(JobParameters jobParameters) {
                        JobParametersBuilder builder = new JobParametersBuilder();
                        builder.addDate("date", new Date());
                        return builder.toJobParameters();
                    }
                })
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }


    @Bean(name = "customReader")
    @StepScope
    public FlatFileItemReader<OverstaptabelRow> customReader(@Value("#{jobParameters['fileName']}") String inputPath) throws Exception {
        OverstaptabelHeaderFieldSetMapper headerMapper = new OverstaptabelHeaderFieldSetMapper();
        FixedLengthTokenizer headerTokenizer = new OverstaptabelHeaderTokenizer();

        OverstaptabelRecordFieldSetMapper recordMapper = new OverstaptabelRecordFieldSetMapper();
        FixedLengthTokenizer recordTokenizer = new OverstaptabelRecordTokenizer();

        OverstaptabelTrailerFieldSetMapper trailerMapper = new OverstaptabelTrailerFieldSetMapper();
        FixedLengthTokenizer trailerTokenizer = new OverstaptabelTrailerTokenizer();

        Map<String, FieldSetMapper<OverstaptabelRow>> fieldSetMappers = new HashMap<String, FieldSetMapper<OverstaptabelRow>>();
        fieldSetMappers.put("01*", headerMapper);
        fieldSetMappers.put("10*", recordMapper);
        fieldSetMappers.put("99*", trailerMapper);

        Map<String, LineTokenizer> lineTokenizers = new HashMap<>();
        lineTokenizers.put("01*", headerTokenizer);
        lineTokenizers.put("10*", recordTokenizer);
        lineTokenizers.put("99*", trailerTokenizer);

        PatternMatchingCompositeLineMapper<OverstaptabelRow> lineMapper = new PatternMatchingCompositeLineMapper<OverstaptabelRow>();
        lineMapper.setFieldSetMappers(fieldSetMappers);
        lineMapper.setTokenizers(lineTokenizers);

        FlatFileItemReader<OverstaptabelRow> reader = new FlatFileItemReader<OverstaptabelRow>();
        File file = new File(inputPath);
        reader.setResource(new UrlResource(file.toURI()));
        reader.setLineMapper(lineMapper);
        reader.open(new ExecutionContext());
        return reader;
    }


    @Bean
    public OverstaptabelRowProcessor customProcessor() {
        return new OverstaptabelRowProcessor();
    }


    @Bean(name = "customStep")
    public Step customStep(@Qualifier("customReader") FlatFileItemReader<OverstaptabelRow> reader, OverstaptabelItemWriter overstaptabelItemWriter) {
        return stepBuilderFactory.get("customStep")
                .<OverstaptabelRow, OverstaptabelRow>chunk(2)
                .reader(reader)
                .processor(customProcessor())
                .writer(overstaptabelItemWriter)
                .build();
    }

    @Bean(name = "customJob")
    public Job customJob(JobCompletionNotificationListener listener, @Qualifier("customStep") Step step) {
        return jobBuilderFactory.get("customJob")
                //    .listener(listener)
                .flow(step)
                .end()
                .build();
    }


    @Bean
    public OverstaptabelItemWriter customWriter() {
        return new OverstaptabelItemWriter();
    }


//***********************************Json*********************/

    @Bean
    public SkipPolicy jsonVerificationSkipper() {
        return new JsonVerificationSkipper();
    }


    @Bean
    public JsonProcessor jsonProcessor() {
        return new JsonProcessor();
    }

    @Bean
    public JsonWriter jsonWriter() {
        return new JsonWriter();
    }


    @Bean(name = "jsonStep")
    public Step jsonStep(JsonFileReader jsonReader, JsonStepListner jsonStepListner) {
        return stepBuilderFactory.get("jsonStep")
                .chunk(2)
                .reader(jsonReader)
                .faultTolerant().skipPolicy(jsonVerificationSkipper())
                .processor(jsonProcessor())
                .writer(jsonWriter())
                .listener(jsonStepListner)
                // .faultTolerant().skip(JsonParsePersonException.class)
                //.skipLimit(10)
                .build();
    }

    @Bean(name = "jsonJob")
    public Job jsonJob(JobCompletionNotificationListener listener, @Qualifier("jsonStep") Step jsonStep) {
        return jobBuilderFactory.get("jsonJob")
                .listener(listener)
                .flow(jsonStep)
                .end()
                .build();
    }

  /*  @Bean
    public  Gson create() {
        return new GsonBuilder()//.registerTypeAdapterFactory(new PersonResultObjectAdapterFactory())
                .setLenient().create();
    }
*/


//***********************************partition job*********************/


    @Bean(name = "partitionUserJob")
    public Job partitionUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("partitionUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(partitionStep())
                .end()
                .build();
    }


    @Bean
    public Step partitionStep() {
        return stepBuilderFactory.get("partitionStep")
                .partitioner("partitioner", partitioner(null))
                .step(slaveStep(null, null))
                .taskExecutor(taskExecutor())
                //  .gridSize(10)
                .build();
    }



    @Bean
    @StepScope
    public PersonProcessor processorPerson() {
        return new PersonProcessor();
    }


    @Bean(name = "slaveStep")
    public Step slaveStep(@Qualifier("partitionUserReader") FlatFileItemReader<Person> reader, JdbcBatchItemWriter<Person> itemWriter) {
        return stepBuilderFactory.get("slaveStep")
                .<Person, Person>chunk(2)
                .reader(reader)
                .processor(processorPerson())
                .writer(itemWriter)
                .taskExecutor(taskExecutorStep())
                .build();
    }


    @Bean(name = "partitionUserReader")
    @StepScope
    public FlatFileItemReader<Person> partitionUserReader(@Value("#{stepExecutionContext[fileName]}") String inputPath) throws Exception {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        File file = new File(inputPath);
        reader.setResource(new UrlResource(file.toURI()));
        setFieldMapper(reader);
        return reader;
    }


    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }


    @Bean
    public TaskExecutor taskExecutorStep() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }


    @Bean
    public MultiFileResourcePartitioner partitioner(AppCommand appCommand ) {
        MultiFileResourcePartitioner partitioner = new MultiFileResourcePartitioner();
        partitioner.setInboundDir("/Users/sujitagarwal/workspace_micro/gs-batch-processing/src/test/resources/csv");
        partitioner.setAppCommand(appCommand);
        return partitioner;
    }

    //***********************************partition job in single file for multi threaded process*********************/

    @Bean
    public RangePartitioner getRangePartitioner() {
        RangePartitioner partitioner = new RangePartitioner();
        return partitioner;
    }

    @Bean(name = "partitionRangeJob")
    public Job partitionRangeJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("partitionRangeJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(partitionRangeStep())
                .end()
                .build();
    }

    @Bean
    public Step partitionRangeStep() {
        return stepBuilderFactory.get("partitionRangeStep")
                .partitioner("getRangePartitioner", getRangePartitioner())
                .step(slaveRangeStep(null, null))
                .taskExecutor(taskRangeExecutor())
                .gridSize(2)
                .build();
    }

    @Bean(name = "slaveRangeStep")
    public Step slaveRangeStep(@Qualifier("partitionRangeReader") FlatFileItemReader<Person> reader, JdbcBatchItemWriter<Person> itemWriter) {
        return stepBuilderFactory.get("slaveRangeStep")
                .<Person, Person>chunk(10)
                .reader(reader)
                .processor(processor())
                .writer(itemWriter)
                .build();
    }

    @Bean(name = "partitionRangeReader")
    @StepScope
    public FlatFileItemReader<Person> partitionRangeReader(@Value("#{stepExecutionContext[fileName]}") String inputPath, @Value("#{stepExecutionContext[fromId]}") int fromId, @Value("#{stepExecutionContext[toId]}") int toId) throws Exception {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>();
        File file = new File(inputPath);
        reader.setResource(new UrlResource(file.toURI()));
        log.info("fromId::"+fromId);
        log.info("toId::"+toId);
        reader.setLinesToSkip(fromId);
        reader.setMaxItemCount(toId);
        setFieldMapper(reader);
        return reader;
    }

    @Bean
    public TaskExecutor taskRangeExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }



}
