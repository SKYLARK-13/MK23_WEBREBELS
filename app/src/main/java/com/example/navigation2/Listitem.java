package com.example.navigation2;

public class Listitem {
    String tittle,date2,link1,department1,circular1;
    public Listitem(){}

    public Listitem(String tittle, String date2, String link1, String department1, String circular1) {
        this.tittle = tittle;
        this.date2 = date2;
        this.link1 = link1;
        this.department1 = department1;
        this.circular1 = circular1;
    }

    public String getTittle() {
        return tittle;
    }

    public String getDate2() {
        return date2;
    }

    public String getLink1() {
        return link1;
    }

    public String getDepartment1() {
        return department1;
    }

    public String getCircular1() {
        return circular1;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public void setLink1(String link1) {
        this.link1 = link1;
    }

    public void setDepartment1(String department1) {
        this.department1 = department1;
    }

    public void setCircular1(String circular1) {
        this.circular1 = circular1;
    }
}
