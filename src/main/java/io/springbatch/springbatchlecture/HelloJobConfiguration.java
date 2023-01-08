package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HelloJobConfiguration {

    //Job실행 -> Step실행 -> Tasklet실행
    private final JobBuilderFactory jobBuilderFactory; //Job을 생성하는 빌더 팩토리
    private final StepBuilderFactory stepBuilderFactory; //step을 생성하는 빌더 팩토리

    @Bean
    public Job helloJob() {
        return this.jobBuilderFactory.get("helloJob") //job 이름 부여
                .start(helloStep1())
                .next(helloStep2())
                .build();
    }

    @Bean
    public Step helloStep1() {
        return stepBuilderFactory.get("helloStep1")// step 이름 부여
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(" ============================");
                        System.out.println(" >> Hello Spring Batch");
                        System.out.println(" ============================");
                        return RepeatStatus.FINISHED; // 기본적으로 Step은 Tasklet을 무한 반복 시키는데 RepeatStatus.FINISHED는 한 번 실행시키고 종료하겠다는 의미
                    }
                })
                .build();
    }
    public Step helloStep2() {
        return stepBuilderFactory.get("helloStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println(" ============================");
                    System.out.println(" >> Step2 has executed");
                    System.out.println(" ============================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
