package com.example.trainingcourse_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "test4";
    private static final int DATABASE_VERSION = 1;
    private static DataBaseHelper instance;

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null || !instance.getDatabaseName().equals(DATABASE_NAME)) {
            instance = new DataBaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User(EMAIL TEXT PRIMARY KEY,PASSWORD TEXT, TYPE TEXT)");

        db.execSQL("CREATE TABLE Student(EMAIL TEXT PRIMARY KEY,FIRST_NAME TEXT, LAST_NAME TEXT,IMAGE BLOB," +
                "MOBILE TEXT, ADDRESS TEXT," +
                "FOREIGN KEY (EMAIL) REFERENCES User(EMAIL))");

        db.execSQL("CREATE TABLE Admin(EMAIL TEXT PRIMARY KEY,FIRST_NAME TEXT, LAST_NAME TEXT,IMAGE BLOB," +
                "FOREIGN KEY (EMAIL) REFERENCES User(EMAIL))");


        db.execSQL("CREATE TABLE Instructor(EMAIL TEXT PRIMARY KEY,FIRST_NAME TEXT, LAST_NAME TEXT,IMAGE BLOB," +
                "MOBILE TEXT, ADDRESS TEXT, SPECIALIZATION TEXT, DEGREE TEXT," +
                "FOREIGN KEY (EMAIL) REFERENCES User(EMAIL))");


        db.execSQL("CREATE TABLE Course(COURSE_ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE TEXT,MAIN_TOPICS TEXT,IMAGE BLOB)");


        db.execSQL("CREATE TABLE Prerequisite(ID_1 INTEGER,ID_PRE INTEGER, PRIMARY KEY (ID_1,ID_PRE)," +
                "FOREIGN KEY (ID_1) REFERENCES Course(COURSE_ID)," +
                "FOREIGN KEY (ID_PRE) REFERENCES Course(COURSE_ID))");

        db.execSQL("CREATE TABLE Instructor_Course(ID_COURSE INTEGER,EMAIL_INST TEXT, PRIMARY KEY (ID_COURSE,EMAIL_INST)," +
                "FOREIGN KEY (ID_COURSE) REFERENCES Course(COURSE_ID) ON DELETE CASCADE," +
                "FOREIGN KEY (EMAIL_INST) REFERENCES Instructor(EMAIL))");

        db.execSQL("CREATE TABLE Section(SECTION_ID INTEGER PRIMARY KEY AUTOINCREMENT,EMAIL TEXT,COURSE_ID INTEGER," +
                " REG_DEADLINE LONG,START_DATE LONG, SCHEDULE TEXT, VENUE TEXT,END_DATE LONG," +
                "FOREIGN KEY (EMAIL) REFERENCES Instructor(EMAIL)," +
                "FOREIGN KEY (COURSE_ID) REFERENCES Course(COURSE_ID) ON DELETE CASCADE)");

        db.execSQL("CREATE TABLE Enrollment(SECTION_ID INTEGER ,EMAIL TEXT," +
                " STATUS TEXT, PRIMARY KEY (SECTION_ID,EMAIL)," +
                "FOREIGN KEY (EMAIL) REFERENCES Student(EMAIL)," +
                "FOREIGN KEY (SECTION_ID) REFERENCES Section(SECTION_ID) ON DELETE CASCADE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUser(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("PASSWORD", user.getPassword());
        contentValues.put("TYPE", user.getType());

        sqLiteDatabase.insert("User", null, contentValues);
    }

    public void insertStudent(Student student) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", student.getEmail());
        contentValues.put("FIRST_NAME", student.getFirstName());
        contentValues.put("LAST_NAME", student.getLastName());
        contentValues.put("IMAGE", student.getPersonalPhoto());
        contentValues.put("MOBILE", student.getMobile());
        contentValues.put("ADDRESS", student.getAddress());

        sqLiteDatabase.insert("Student", null, contentValues);
    }

    public void insertAdmin(Admin admin) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", admin.getEmail());
        contentValues.put("FIRST_NAME", admin.getFirstName());
        contentValues.put("LAST_NAME", admin.getLastName());
        contentValues.put("IMAGE", admin.getPersonalPhoto());

        sqLiteDatabase.insert("Admin", null, contentValues);
    }

    public void insertInstructor(Instructor instructor) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", instructor.getEmail());
        contentValues.put("FIRST_NAME", instructor.getFirstName());
        contentValues.put("LAST_NAME", instructor.getLastName());
        contentValues.put("IMAGE", instructor.getPersonalPhoto());
        contentValues.put("MOBILE", instructor.getMobile());
        contentValues.put("ADDRESS", instructor.getAddress());
        contentValues.put("SPECIALIZATION", instructor.getSpecialization());
        contentValues.put("DEGREE", instructor.getDegree());

        sqLiteDatabase.insert("Instructor", null, contentValues);
    }

    public void insertCourse(Course course) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", course.getTitle());
        contentValues.put("MAIN_TOPICS", course.getMainTopics());
        contentValues.put("IMAGE", course.getImage());

        sqLiteDatabase.insert("Course", null, contentValues);
    }

    public void insertPrerequisite(int id1, int id_pre) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_1", id1);
        contentValues.put("ID_PRE", id_pre);

        sqLiteDatabase.insert("Prerequisite", null, contentValues);
    }

    public void insertInstructor_Course(int id, String email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_COURSE", id);
        contentValues.put("EMAIL_INST", email);

        sqLiteDatabase.insert("Instructor_Course", null, contentValues);
    }

    public void insertSection(Section section) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", section.getEmail());
        contentValues.put("COURSE_ID", section.getCourseId());
        contentValues.put("REG_DEADLINE", section.getRegDeadLine());
        contentValues.put("START_DATE", section.getStartDate());
        contentValues.put("SCHEDULE", section.getSchedule());
        contentValues.put("VENUE", section.getVenue());

        sqLiteDatabase.insert("Section", null, contentValues);
    }

    public void insertEnrollment(String email, String section_id, String status) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SECTION_ID", section_id);
        contentValues.put("EMAIL", email);
        contentValues.put("STATUS", status);

        sqLiteDatabase.insert("Enrollment", null, contentValues);
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // Execute the query
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT EXISTS (SELECT * FROM User WHERE EMAIL = '" + email + "')",
                null);

        // Get the result value
        boolean exists = false;
        if (cursor != null && cursor.moveToFirst()) {
            exists = cursor.getInt(0) == 1;
        }

        // Close the cursor and database
        cursor.close();
        sqLiteDatabase.close();

        return exists;
    }

    public Cursor getAllCourses() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT  COURSE_ID, TITLE FROM Course", null);
    }

    public Cursor returnUserValues(String email){
        if(checkUserExists(email)){
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            return sqLiteDatabase.rawQuery("SELECT * FROM User WHERE EMAIL = '" + email + "'", null);
        }
        return null;
    }

    public Cursor getAllAvailableSections(){
        long currentTimeMillis = System.currentTimeMillis();
        long timeInSecond = currentTimeMillis / 1000;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT s.SECTION_ID, c.TITLE \n" +
                "FROM Course c \n" +
                "JOIN Section s ON c.COURSE_ID = s.COURSE_ID \n" +
                "WHERE " + timeInSecond + " < s.REG_DEADLINE;";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getSectionInfo(String sectionId){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT s.SECTION_ID,c.COURSE_ID,c.TITLE,c.MAIN_TOPICS,c.IMAGE," +
                "i.FIRSTNAME, i.LASTNAME, s.REG_DEADLINE, s.START_DATE, s.SCHEDULE , s.VENUE , s.END_DATE \n" +
                "FROM Course c \n" +
                "JOIN Section s ON c.COURSE_ID = s.COURSE_ID " +
                "JOIN Instructor i ON s.EMAIL = i.EMAIL \n" +
                "WHERE s.SECTION_ID = "+ sectionId +";";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getCoursePrerequisites(String course_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT p.ID_PRE \n" +
                "FROM Course c \n" +
                "JOIN Prerequisite p ON p.ID_1 = c.COURSE_ID \n" +
                "WHERE c.COURSE_ID = " + course_id + " ;";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getEndDateOfSection(String email,String course_id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT s.END_DATE \n" +
                "FROM Section s \n" +
                "JOIN Enrollment e ON e.COURSE_ID = s.COURSE_ID \n" +
                "WHERE e.EMAIL = '"+ email +"' and e.COURSE_ID = " + course_id + " ;";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getCurrentCourses(String email, String start_date, String end_date){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT s.SCHEDULE \n" +
                "FROM Section s \n" +
                "JOIN Enrollment e ON e.COURSE_ID = s.COURSE_ID \n" +
                "WHERE e.EMAIL = '"+ email +"' and (" +
                "( " + start_date + " >= s.START_DATE and " + start_date + " <= s.END_DATE ) OR " +
                "( " + end_date + " >= s.START_DATE and " + end_date + " <= s.END_DATE ) OR " +
                "( " + start_date + " < s.START_DATE and " + end_date + " > s.END_DATE ));";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getAllStudiedCourses(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        long currentTimeMillis = System.currentTimeMillis();
        long timeInSecond = currentTimeMillis / 1000;
        String query = "SELECT s.SECTION_ID, c.TITLE \n" +
                "FROM Course c \n" +
                "JOIN Section s ON c.COURSE_ID = s.COURSE_ID \n" +
                "JOIN Enrollment e ON s.EMAIL = e.EMAIL \n" +
                "WHERE e.EMAIL = '" + email + "' and e.STATUS = 'Accepted' and " +
                " " + timeInSecond + " > s.END_DATE ;";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getAllSections(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT  s.SECTION_ID, c.TITLE " +
                "FROM Course c \n" +
                "JOIN Section s ON c.COURSE_ID = s.COURSE_ID;";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getStudentInfo(String email){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT  s.FIRST_NAME, s.LAST_NAME, s.MOBILE, s.ADDRESS, u.PASSWORD ,s.IMAGE " +
                "FROM User u \n" +
                "JOIN Student s ON s.EMAIL = u.EMAIL \n" +
                "WHERE s.EMAIL = '" + email + "' ;";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public void editData(String table, String condition, String column, String newValue, String keyCol){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, newValue);

        sqLiteDatabase.update(table, values, keyCol + " = ?", new String[]{condition});
    }


    public void editDataImage(String table, String condition, String column, byte[] newValue, String keyCol) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, newValue);

        sqLiteDatabase.update(table, values, keyCol + " = ?", new String[]{condition});
    }

    public Cursor NextPossibleCourse(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM Course;";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public Cursor getCourseInfo(String courseId){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT  c.TITLE, c.MAIN_TOPICS " +
                "FROM Course c \n" +
                "WHERE c.COURSE_ID = " + courseId + " ;";
        return sqLiteDatabase.rawQuery(query, null);
    }

    public void deleteData(String table, String condition, String keyCol){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(table, keyCol + " = ?", new String[]{condition});
    }

    public Cursor getAllInstructors(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT  EMAIL, FIRST_NAME, LAST_NAME" +
                " FROM Instructor", null);
    }

    public boolean checkInstructorCourse(String email,int courseId) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        // Execute the query
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT EXISTS (SELECT * FROM Instructor_Course " +
                        "WHERE EMAIL_INST = '" + email + "' and ID_COURSE = " + courseId + ")",
                null);

        // Get the result value
        boolean exists = false;
        if (cursor != null && cursor.moveToFirst()) {
            exists = cursor.getInt(0) == 1;
        }

        // Close the cursor and database
        cursor.close();
        sqLiteDatabase.close();

        return exists;
    }
}


