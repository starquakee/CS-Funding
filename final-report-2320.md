# Team Report

## Metrics

- Lines of Code

  ![1684755930384](final-delivery-img/1684755930384.png)
  L(J): Lines of Java

  LOC: Lines of code

- Number of modules/subprojects

  ![1684755974693](final-delivery-img/1684755974693.png)



- Number of packages

  ![1684243710806](final-delivery-img/1684243710806.png)

- Number of source files

  ![1684755135128](final-delivery-img/1684755135128.png)

- Number of 3rd party dependencies

  ![1684755698404](final-delivery-img/1684755698404.png)

![1684755729757](final-delivery-img/1684755729757.png)

We could also use metrics to estimate the maintainability of software systems. Please also answer:

- What is the maintainability of your team project?

  ![1684760251050](final-delivery-img/1684760251050.png)

  ![1684760274848](final-delivery-img/1684760274848.png)

  [team-project-2320/maintainability.pdf at main · sustech-cs304/team-project-2320 · GitHub](https://github.com/sustech-cs304/team-project-2320/blob/main/maintainability.pdf)
  
  this report is export by Code Climate

## Documentation for Users

![1685539970201](final-delivery-img/1685539970201.png)

login & sign up

![1685540164394](final-delivery-img/1685540164394.png)

sign up

![1685540333125](final-delivery-img/1685540333125.png)

main page for admin

![1685540402661](final-delivery-img/1685540402661.png)

main page for user

![1685540351615](final-delivery-img/1685540351615.png)

send apply

![1685540450588](final-delivery-img/1685540497435.png)

check apply

![1685540536703](final-delivery-img/1685540536703.png)

view funding(in group)

![1685540651935](final-delivery-img/1685540651935.png)

visualization

![1685540693813](final-delivery-img/1685540693813.png)

edit group

## Documentation for developers

### ApplyController

```java
@Data
public class ApplyDTO {
    private String name;
    private int money;
    private String type1;
    private String type2;
    private int fundID;
    private int researchGroupID;
    private int userID;
    private String state;
    private String contentSummary;
    private String remark;
    private int apply;
}
```

apply

```java
	@PostMapping(value = "/register/testapply")
    public String testAddApply(@RequestBody ApplyDTO applyDTO) {
        ...
    }
```

test to add an apply

```java
	@PostMapping(value = "/register/apply")
    public Result AddApply(@RequestBody ApplyDTO applyDTO) {
        ...
    }
```

add an apply

```java
	@PostMapping(value = "/register/resubmit")
    public Result ResubmitApply(@RequestBody ApplyDTO applyDTO) {
        ...
    }
```

resubmit an apply if it was rejected

```java
    @PostMapping(value = "/inspect-apply")
    public Result testJudgeApply(@RequestBody InspectDTO inspectDTO) {
        ...
    }
```

examine an apply

```java
    @RequestMapping(value = "/search-all-apply", method = {RequestMethod.GET, RequestMethod.POST})
    public Result searchAllApply(@RequestBody ApplySearchDTO searchDTO) {
        ...
    }
```

search apply by group, fund and state

```java
    @RequestMapping(value = "/search-apply-userid", method = {RequestMethod.GET, RequestMethod.POST})
    public Result searchApplyUserID(@RequestBody ApplySearchDTO searchDTO, @RequestParam int uid){
        ...
    }
```

get apply by user, group, fund and state

```java
    @GetMapping("/get-all-apply")
    public Result getAllApply() {
        ...
    }
```

get all apply

```java
    @GetMapping("/all-apply-fund")
    public Result getApplyByFundID(@RequestParam int fundID) {
        ...
    }
```

get apply by fund

```java
    @GetMapping("/selectapplybyresearchgroupid")
    public Result getApplyByResearchGroupID(int researchGroupID) {
        ...
    }
```

get apply by group

```java
    @GetMapping("/apply-userid")
    public Result getSortApplyByUserID(int userID) {
        ...
    }
```

get apply by user

```java
    @GetMapping("/my-apply")
    public Result getMyApply() {
        ...
    }
```

get current user's apply

```java
    @RequestMapping(value = "/search-my-apply", method = {RequestMethod.GET, RequestMethod.POST})
    public Result searchMyApply(@RequestBody ApplySearchDTO applySearchDTO){
        ...
    }
```

get current user's apply by group, fund and state

### Fund_ApplyController

```java
@Data
public class Fund_ApplyDTO {
    private int fundUUID;
    private int applyUUID;
}
```

fund_apply is to link fund and apply

```java
	@PostMapping(value = "/register/fund_apply")
    public Result testAddFundApply(@RequestBody Fund_ApplyDTO fund_applyDTO) {
        ...
    }
```

add a fund_apply

```java
    @GetMapping("/apply-by-fund")
    public Result getApplyByFund(@RequestParam int fid){
        ...
    }
```

get fund's apply

### FundController

```java
@Data
public class FundDTO {
    private String fundNumber;
    private String fundName;
    private float sum;
    private float balance;
    private int remainDays;
    private String startTime;
    private String endTime;
    private int uuid;
}
```

fund

```java
    @PostMapping(value = "/register/fund")
    public String testAddFund(@RequestBody FundDTO fundDTO) {
        ...
    }
```

add a fund

```java
    @PostMapping(value = "/modify/fund")
    public Result testModifyFund(@RequestBody FundDTO fundDTO) {
        ...
    }
```

modify a fund

```java
    @GetMapping("delete/fund")
    public Result testDeleteFund(@RequestParam int uuid) {
        ...
    }
```

delete a fund

```java
    @GetMapping("/getallfunds")
    public Result getAllFund(){
        ...
    }
```

get all fund

```java
    @GetMapping("/get-fund")
    public Result getFundByUuid(@RequestParam int uuid) {
        ...
    }
```

get fund by id

```java
    @GetMapping("/get-fund-vague")
    public Result getFundVague(@RequestParam String FundNumber,@RequestParam String FundName) {
        ...
    }
```

get fund vaguely by number and name

### LoginController

```java
@Data
public class LoginDTO {
    private String name;
    private String key;
}
```

login

```java
    @PostMapping(value = "/api/login")
    public Result login(@RequestBody LoginDTO loginDTO){
        ...
    }
```

try to login

### NoticeController

```java
@Data
public class NoticeDTO {
    private String content;
    private String time;
    private int uuid;
    private int noticeFrom;
    private int noticeTo;
}
```

notice

```java
    @PostMapping(value = "/register/notice")
    public String testAddNotice(@RequestBody NoticeDTO noticeDTO) {
        ...
    }
```

add a notice

```java
    @GetMapping("delete/notice")
    public Result testDeleteNotice(@RequestParam int uuid) {
        ...
    }
```

delete a notice

```java
    @GetMapping("/getallnotices")
    public Result testGetAllNotice(){
        ...
    }
```

get all notice

```java
@GetMapping("/get/notice")
    public Result testGetNoticeByID(@RequestParam int uuid){
        ...
    }
```

get notice by id

### ResearchGroup_FundController

```java
@Data
public class ResearchGroup_FundDTO {
    private int researchGroupUUID;
    private int fundUUID;
}
```

researchGroup_fund is to link fund and group

```java
    @PostMapping(value = "/register/research-group_fund")
    public Result testAddResearchGroupFund(@RequestBody ResearchGroup_FundDTO researchGroup_fundDTO) {
        ...
    }
```

add a researchGroup_fund

```java
    @GetMapping("/fund-research-group")
    public Result getFundByResearchGroup(int gid){
        ...
    }
```

get fund by group

### ResearchGroupController

```java
public class ResearchGroupDTO {
    private String teacher;
    private float allFund;
}
```

group

```java
    @PostMapping(value = "/register/researchgroup")
    public String testAddResearchGroup(@RequestBody ResearchGroupDTO researchGroupDTO) {
        ...
    }
```

add a group

```java
    @GetMapping("/get-all-research-groups")
    public Result getAllResearchGroup() {
        ...
    }
```

get all group

```java
    @GetMapping("/get-research-groups-by-name")
    public Result getResearchGroupByName(String teacherName) {
        ...
    }
```

get group by teacher's name

```java
    @GetMapping("/get-current-group")
    public Result getCurrentGroup() {
        ...
    }
```

get current user's group

```java
    @GetMapping("/get-research-groups-by-user")
    public Result getResearchGroupByUser(int UserID) {
        ...
    }
```

get group by user

```java
    @GetMapping("/current-group-data")
    public Result getCurrentResearchGroupData() {
        ...
    }
```

same as "/get-current-group"

```java
    @GetMapping("/user-group-fund-data")
    public Result getUserResearchGroupFundData(@RequestParam int uid) {
        ...
    }
```

get user's group's fund

```java
    @GetMapping("/current-group-fund-data")
    public Result getCurrentResearchGroupFundData() {
        ...
    }
```

get current user's group's fund

### User_ResearchGroupController

```java
public class User_ResearchGroupDTO {
    private int userUUID;
    private int researchGroupUUID;
}
```

user_researchGroup is to link user and group

```java
    @PostMapping(value = "/register/user_researchgroup")
    public Result testAddUserResearchGroup(@RequestBody User_ResearchGroupDTO user_researchGroupDTO) {
		...
    }
```

add a user_researchGroup

```java
    @GetMapping("/selectresearchgroupbyuser")
    public Result getResearchGroupByUser(@RequestParam int uid) {
        ...
    }
```

get user's group

```java
    @GetMapping("/current-group-id")
    public Result getCurrentGroup() {
        ...
    }
```

get current user's group

### UserController

```java
@Data
public class UserDTO {
    private String key;
    private String phoneNum;
    private String name;
}
```

user

```java
    @PostMapping(value = "/register/user")
    public String testAddUser(@RequestBody UserDTO userDTO) {
        ...
    }
```

add a user

```java
    @GetMapping("/all-users")
    public Result getAllUser() {
        ...
    }
```

get all user

```java
    @GetMapping("/getalladmins")
    public List<User> getAllAdmin(){
        ...
    }
```

get all admin

```java
    @GetMapping("/user-id")
    public Result getUserByID(@RequestParam String id) {
        ...
    }
```

get user by id

```java
    @GetMapping("/current-user")
    public Result getCurrentUser() {
        ...
    }
```

get current user

```java
    @GetMapping("/user-phone")
    public Result getUserByPhoneNum(String id) {
        ...
    }
```

get user by phone number
## Tests
### Testing tools and tests kinds
We do automated tests for our backend codes. And we use following 5 tools to come it true.
1. JUnit: JUnit is a unit testing framework for Java that allows you to write and run repeatable tests. It provides annotations such as `@Test` `@BeforeEach` to define test methods and assertions to verify expected outcomes.

2. Mockito: Mockito is a mocking framework for Java that allows to create mock objects and define their behavior. It is used for creating mock instances of dependencies and setting up expectations on their interactions.

3. Spring's MockMvc: MockMvc is a testing framework provided by Spring for testing Spring MVC controllers without making actual HTTP requests. It allows to simulate HTTP requests and verify the responses. It is used for testing the behavior of each `ClassController` class.

4. Hamcrest: Hamcrest is a library that provides a set of matchers for creating more readable assertions in tests. It enhances the readability of test assertions by providing a fluent API for expressing expected outcomes.

5. ObjectMapper: ObjectMapper is a part of the Jackson library and is used for converting Java objects to JSON and vice versa. It is used in the code to convert objects to JSON strings for request payloads in the tests.

These testing tools are commonly used in Java development to write unit tests and integration tests to ensure the correctness and robustness of the code.
### Source code of related artifact for testing
First, we inject mocks we need in our tests, and then use `@BeforeEach` to init mocks.

![code1](/final-delivery-img/code1.png)
Then, we new DTO we need and use "when thenReturn" to design pre-returned results of Service.
Finally, we use mockMvc to perform the api and to assert the real results.

![code1](/final-delivery-img/code2.png)
![code1](/final-delivery-img/code3.png)
We put our test codes in here: https://github.com/sustech-cs304/team-project-2320/tree/main/src/test/java/com/cs304/csfunding/controller
### Test execution results
We run our automated tests successfully as following:

![code1](/final-delivery-img/result.png)
![code1](/final-delivery-img/result1.png)
![code1](/final-delivery-img/result2.png)
![code1](/final-delivery-img/report1.png)
And this is our tests report. In our tests, we meet some nullpointer problems, but in the actual use, they don't exist. Even so, in the end, our test coverage is over 80 percent, which means the test is working well.
![code1](/final-delivery-img/report2.png)
We push the report in here: https://github.com/sustech-cs304/team-project-2320/blob/main/target/site/jacoco/index.html
## Build
### Framework/Technology/Tools/Plugins
#### 1 Framework: SpringBoot
SpringBoot provides Java developers with a platform to get started with an auto configurable production-grade Spring application. With it, developers can get started quickly without losing time on preparing and configuring their Spring application.
![tupian](/final-delivery-img/build.png)
#### 2 Technology/Tools
+ Mysql: MySQL is a relational database management system. Databases are the essential data repository for all software applications.
+ Lombok: Lombok is a library that helps reduce boilerplate code in Java applications. It provides annotations that generate code during compilation time.
![tupian](/final-delivery-img/build-sql-lombok.png "views in pom.xml")
#### 3 Plugins
+ SpringBoot Maven Plugin: It provides a connection between Spring project and Maven. The connection helps the developer test and package the project with high efficiency.
+ Jacoco Maven Plugin: The Jacoco Maven Plugin is used to generate code coverage reports for unit tests. It provides a way to measure how much of the code is covered by unit tests. The plugin generates a report that shows the percentage of code that is covered by unit tests.

### Tasks during building
+ Configure the project
+ Set the filter for web content and interaction
+ Embed the project on the web server with Tomcat
+ Set up the context for project
+ Start service

### How to run the build
#### 1 Frontend
Run the command below to build frontend
```
npm run build
```
#### 2 Backend
Run the package function of maven in terminal
```
maven package
```
Then it will produce a .jar file in target directory

![tupian](final-delivery-img/jar-back.png)

### Buildfile for project build
#### 1 Frontend build
Buildfile for our project's frontend building is /csfunding-frontend/dockerfile. package*.json including package.json and package-lock.json is involved to help building process.

![tupian](final-delivery-img/dockerfile_frontend.png)


build result:

![tupian](final-delivery-img/artifacts-front.png)
#### 2 Backend build
Our project uses pom.xml for the whole backend building process. 

![tupian](final-delivery-img/buildfile.png)

The artifacts are involved in below picture.

![tupian](final-delivery-img/build.png)

## Deployment

### Technology/Tools: Docker
Docker is an open-source engine that automates the deployment of applications into containers. It is an open platform for developing, shipping, and running applications. Docker enables us to separate our applications from our infrastructure so us can deliver software quickly. With Docker, we can manage our infrastructure in the same ways we manage our applications.

Because our project is split into frontend and backend, they use different framework. In order to containerize them, we use two docker which use two different base images.

### Backend: Springboot

#### Preparation for containerization
The springboot application needs to first build to a jar file to run.

![](final-delivery-img/docker/mvn-package-1.png)

![](final-delivery-img/docker/mvn-package-2.png)

#### Containerization

dockerfile:

```dockerfile
FROM openjdk:8
COPY /target/*.jar /app.jar
CMD ["--server.port=8081"]
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app.jar"]
```

The process and result for image build

![tupian](final-delivery-img/deploymenting.png)

![tupian](final-delivery-img/deploymented.png)

Run the image in container
![tupian](final-delivery-img/containerization.png)
The proof for the success of containerization:
![tupian](final-delivery-img/test%20containerization.png)
The first try is failed by the wrong instruction. The second try is an approximate success because 415 means the CsFunding System is running properly. The third try is a success because we login into the system successfully and get a token from container.

### Frontend: Vue+Vite

#### Preparation for containerization

We need to build the frontend project into a html file to containerize.

![](final-delivery-img/docker/frontend-build.png)

It will generate a folder dist, which can be deployed to a http server.

#### Containerization

dockerfile:

```dockerfile
FROM node:lts-alpine
RUN npm install -g http-server
COPY package*.json ./
RUN npm install
COPY dist ./
EXPOSE 8080
CMD ["http-server", "dist"]
```

docker build:

![](final-delivery-img/docker/frontend-docker-build1.png)

docker run:

![](final-delivery-img/docker/frontend-docker-run.png)

Then we can visit the site for frontend. With backend docker running at the same time, The application can run normally.