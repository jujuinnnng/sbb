# 📢 sbb프로젝트
## `sbb(Spring Boot Backend)` </br>

---

## 📒 ONE: 서비스 내용  </br>
> 프로젝트를 시작하게 된 계기는 실무에서 진행했던 것 중에 Spring Boot, JPA를 사용한 경험이 없어, 보다 다양한 개발 환경의 프로젝트를 경험하고 싶어 초심자의 마음으로 웹 프로그래밍의 기본 소양이라 할 수 있는 게시판 생성부터 서버 구성, 배포까지 만드는 과정을 담았습니다.

## 📒 TWO: 서버 구성 </br>
### ✒️ 1. `시스템 환경설정 ` 
* JDK 17
* Gradle 8.8
* JPA
* Spring Boot
* postgres 16.6

### ✒️ 2. `운용 포트`
* API 서비스 - locahost:8080

### ✒️ 3. `nginx` 구성  
- SBB 서비스의 엔진엑스 설정 파일 루트 권한생성 -  HTTPS로 전환

   ```
   ubuntu@sbb:/etc/nginx/sites-available$ sudo vim sbb80  
   ```
- sbb80

    ```
    server {  
           listen 80;  
           server_name sbb서버이름; 
           rewrite        ^ https://$server_name$request_uri? permanent;  
        }  
        
    server { 
           listen 443 ssl;
           server_name sbb서버이름;
        
           ssl_certificate /etc/letsencrypt/live/sbb서버이름/fullchain.pem; # managed by Certbot
           ssl_certificate_key /etc/letsencrypt/live/sbb서버이름/privkey.pem; # managed by Certbot
           include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot

           location / {
               
              .. 생략 ..
    
          }
    }
  ```

### ✒️ 4. `도메인` 적용 </br>
- (도메인 사이트 가비아 사용) </br></br>
  <img width="80%" src="https://github.com/user-attachments/assets/7ba832f7-1a6b-4f25-9a2e-b90200cd24ab"/>


### ✒️ 5. `배포` 과정
- SFTP로 수동 배포

### ✒️ 6. `서버 실행 종료` 과정
- 1) 서비스 시작 스크립트
    ```
   ubuntu@sbb:~$ cd sbb
   ubuntu@sbb:~/sbb$ vim start.sh
    ```

-  파일명: /home/ubuntu/sbb/start.sh
    ```
    #!/bin/bash

    JAR=java -jar -Dspring.profiles.active=develop sbb-0.0.1-SNAPSHOT.jar
    LOG=/home/ubuntu/sbb/sbb.log

    nohup java -jar $JAR > $LOG 2>&1 &
    ```

- 2) 서비스 종료 스크립트

    ``` 
    ubuntu@sbb:~/sbb$ ./stop.sh
    ```
- 파일명: /home/ubuntu/sbb/stop.sh
   
    ``` 
    #!/bin/bash
    
    SBB_PID=$(ps -ef | grep java | grep sbb | awk '{print $2}')
    
    if [ -z "$SBB_PID" ];
    then
    echo "SBB is not running"
    else
    kill -9 $SBB_PID
    echo "SBB stopped."
    fi
    ``` 