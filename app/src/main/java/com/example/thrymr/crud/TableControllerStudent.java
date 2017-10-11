package com.example.thrymr.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableControllerStudent extends DataBaseHandler {

    public TableControllerStudent(Context context) {
        super(context);
    }

    public boolean create(Student objectStudent) {

        ContentValues values = new ContentValues();

        values.put("name", objectStudent.name);
        values.put("email", objectStudent.email);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("student", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public int count() {

        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM student";
        int recordCount = db.rawQuery(sql, null).getCount();
        db.close();

        return recordCount;

    }

    public List<Student> read() {

        List<Student> recordsList = new ArrayList<Student>();

        String sql = "SELECT * FROM student ORDER BY id DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {

                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                String studentFirstname = cursor.getString(cursor.getColumnIndex("name"));
                String studentEmail = cursor.getString(cursor.getColumnIndex("email"));

                Student objectStudent = new Student();
                objectStudent.id = id;
                objectStudent.name = studentFirstname;
                objectStudent.email = studentEmail;

                recordsList.add(objectStudent);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

    public Student readSingleRecord(int studentId) {

        Student objectStudent = null;

        String sql = "SELECT * FROM student WHERE id = " + studentId;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {

            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
            String firstname = cursor.getString(cursor.getColumnIndex("name"));
            String email = cursor.getString(cursor.getColumnIndex("email"));

            objectStudent = new Student();
            objectStudent.id = id;
            objectStudent.name = firstname;
            objectStudent.email = email;

        }

        cursor.close();
        db.close();

        return objectStudent;

    }

    public boolean update(Student objectStudent) {

        ContentValues values = new ContentValues();

        values.put("name", objectStudent.name);
        values.put("email", objectStudent.email);

        String where = "id = ?";

        String[] whereArgs = {Integer.toString(objectStudent.id)};

        SQLiteDatabase db = this.getWritableDatabase();

        boolean updateSuccessful = db.update("student", values, where, whereArgs) > 0;
        db.close();

        return updateSuccessful;

    }


    public boolean delete(int id) {
        boolean deleteSuccessful = false;

        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("student", "id ='" + id + "'", null) > 0;
        db.close();

        return deleteSuccessful;

    }
}