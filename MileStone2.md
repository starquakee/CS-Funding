# MileStone2 Report

## 1. Requirements Modeling & Design

### 1.1 Use Case Diagram (2 points)

### 1.2 Swimlane Diagram (for one of the use cases) (1 point)
![Swimlane Diagram](milstone2_pictures/泳道图.png)
### 1.3 Natural Language Description (for one of the use cases) (1 point)

### 1.4 Diagrams for Logical view (2 points)

### 1.5 Data Design (2 points)
First, we deal with user information, including the user's name, phone number, group affiliation, and so on. Next, we collect form relationships, including user ID, date of application, reimbursement amount, grant category, review status, summary of content, and remarks.


We then designed four tables to gather information about the class. In order to improve database performance, store old data and dirty data more conveniently, and prevent dependency transfer, we did not use foreign keys in the database, but added three tables representing one-to-many relationship. The four tables we designed are fund, apply, user, researchgroup. The fund table contains the fund number and the fund name, both of which are string, and then it stores Sum and Balance as float, preferably with the start and end times. The apply table contains string name, float money, two types, the funding ID, the research group ID, three review states, a summary of content up to 500 length, and comments up to 100 length. User class, including account password, phone number, user name. The research group category includes teachers and AllFund.
![ER](milstone2_pictures/ER.png)
### 1.6 UI Design (2 points)

## 2. Collaborations (1 point)

## 3. Deliverables (1 point)

## 4. Team Presentation & Demo (2 points)

