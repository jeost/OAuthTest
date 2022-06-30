package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 기본설정 해주는애
public class AppStart {
    public static void main(String[] args) { // 코드 읽어주는 스레드
        SpringApplication.run(AppStart.class); // 여기서부터 프로젝트가 시작, 이 클래스보다 위의 디렉토리는 못읽음
    }
}
