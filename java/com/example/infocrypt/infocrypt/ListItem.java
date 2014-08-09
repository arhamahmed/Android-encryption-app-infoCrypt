package com.example.infocrypt.infocrypt;

import java.io.Serializable;

/*
 Created by Arham on 2014-07-07

 Class: ListItem - represents an entry in the listview
 Purpose: represents a single item in a listview

 Fields:
    title: title of list entry
    descrip: description of list entry

 Methods:
    getTitle: returns current title
    getDescrip: returns current description
    setTitle: sets title to given string
    setDescrip: sets description to given string
 */

public class ListItem implements Serializable{

    private String title;
    private String descrip;

    public ListItem(String userTitle, String userDescrip) {
        this.title = userTitle;
        this.descrip = userDescrip;
    }

    //returns the title of a list item
    public String getTitle() {
        return this.title;
    }

    //reurns the description of a list item
    public String getDescrip() {
        return this.descrip;
    }

    //sets the title of a list item
    public void setTitle(String x) {this.title = x;}

    //sets the description of a list item
    public void setDescrip (String x) {this.descrip = x;}

    //overloads the toString() method and returns the title+description of a list item
    public String toString() {
        return this.title + ": " + this.descrip;
    }
}
