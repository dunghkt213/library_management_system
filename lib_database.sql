show databases ;
create database lib;
use lib;

#Tao bang Categories - Thể loại sách
create table Categories (
    categoryID int primary key auto_increment,
    categoryName varchar(100) not null
);

#Tao bang Books
create table Books (
    bookID varchar(20) primary key ,
    bookTitle varchar(100) not null ,
    bookAuthor varchar(100) not null ,
    bookPublisher varchar(100),
    edition varchar(50),
    language varchar(50),
    quantity int not null default 0,
    remainingBooks int not null default 0,
    availability enum('Available', 'Not Available') not null ,
    categoryID int,
    foreign key (categoryID) references Categories(categoryID)
);

#Tao bang Students
create table Students (
    studentID int primary key ,
    studentName varchar(100) not null ,
    phoneNumber varchar(15),
    studentEmailAddress varchar(100),
    birthdayDate date,
    major varchar(100)
);

#Tao bang Loans - Cho mượn sách
create table Loans (
    loansID int primary key auto_increment,
    bookID varchar(20),
    studentID int,
    loadDate date not null ,
    returnDate date,
    dueDate date not null ,
    status enum ('Active', 'Returned', 'Overdue') not null ,
    foreign key (bookID) references Books(bookID),
    foreign key (studentID) references Students(studentID)
);

#Tao bang LoanHistory (Lich su muon sach)
create table LoanHistory (
    historyID int primary key auto_increment,
    bookID varchar(20),
    studentID int ,
    loanDate date not null ,
    returnDate date,
    dueDate date not null ,
    foreign key (bookID) references Books(bookID),
    foreign key (studentID) references Students(studentID)
);

#Tao bang phi phat
create table Fines (
    fineID int primary key auto_increment,
    loanID int,
    fineAmount decimal (10, 2) not null ,
    status enum ('Paid', 'Unpaid') not null ,
    foreign key (loanID) references Loans(loansID)
);

#Tao bang Staff - Nhân viên thư viện
create table Staff (
    staffID int primary key,
    firstName varchar(50) not null ,
    lastName varchar(50) not null ,
    email varchar(100),
    phoneNumber varchar(15),
    position varchar(50)
);

show tables ;