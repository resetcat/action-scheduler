 action-scheduler
---
### Project Description
1. simple Spring boot app for some action execution
2. runs events on times specified in CSV file
3. once launched it parses through file every minute by default.
---
### Requirements:
Have java installed on your system. You can download it here https://www.java.com/download/

---
### Configuration
1. Copy this project: `git clone git@github.com:resetcat/action-scheduler.git`<br />
2. Run the project in console : `mvn spring-boot:run`
3. To change parse timer enter in console while launching :
`mvn spring-boot:run -Dfixed.delay.seconds={Your time in seconds}`
4. To change execution times you need to go to `src/main/resources/` from the root folder and open file `schedule.
   csv` with any text editor.
---
