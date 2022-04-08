# Labb2-s-m-s

<------TEACHER------->

PUT: localhost:8080/student-management-system/api/teachers/addCourseToTeacher/"courseID"/"teacherID"
Lägger till en course(subject) till en teacher

DEL: localhost:8080/student-management-system/api/teachers/"id"
Tar bort teacher med det id du angett.

GET: localhost:8080/student-management-system/api/teachers/"id"
hämtar en teacher med det id du angett.

GET: localhost:8080/student-management-system/api/teachers
hämtar alla teachers som finns.

POST: localhost:8080/student-management-system/api/teachers/create
Skapar en ny teacher.

Json format:
{
	"firstName": "Peter",
	"lastName": "Petersson",
	"email": "Peter@gmail.com"
}

<-----COURSE------>

PUT: localhost:8080/student-management-system/api/courses/CourseStudent/"courseID"/"studentID"
Lägger till studenter till en viss course.

DEL: localhost:8080/student-management-system/api/courses/"id"
tar bort course med det id du angett.

GET: localhost:8080/student-management-system/api/courses/"id"
hämtar en course med det id du angett.

GET: localhost:8080/student-management-system/api/courses
hämtar alla courses som finns.

POST: localhost:8080/student-management-system/api/courses/create
Skapar en ny course.

Json format: 
{
	"subject": "Java"
}

<-----STUDENT------>

POST: localhost:8080/student-management-system/api/students/create
Skapar en student
inga mellanslag/nummer/tecken på firstname och lastname.
email måste ha @ och . och inga mellanslag

Json format : 
{
	"firstName": "Peter",
	"lastName": "Petersson",
	"email": "Peter@gmail.com",
	"phoneNumber": "0707234123"
}

GET: localhost:8080/student-management-system/api/students/"id"
hämtar en student med det id du angett.

GET: localhost:8080/student-management-system/api/students/
hämtar alla students som finns.


GET: localhost:8080/student-management-system/api/students/getlastname?lastName="last name"
hämtar alla vid namn av det efternamn du angett.


DELETE: localhost:8080/student-management-system/api/students/"id"
Tar bort student med det id du angett.


PATCH: localhost:8080/student-management-system/api/students/update/"id"?firstname="new first name"
Byter namn till det namn du angett, på den studenten med det id du angett.


PATCH: localhost:8080/student-management-system/api/students/update/email/"id"
Byter email med json format på den student med det id du angett.

Json format : 
{
	"email": "Berit@gmail.com"
}
