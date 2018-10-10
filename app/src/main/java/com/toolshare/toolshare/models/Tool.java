package com.toolshare.toolshare.models;

public class Tool {
    private int Id;
    private String Owner;
    private int TypeId;
    private String Name;
    private int Year;
    private String Model;

    public Tool() {
        this.Id = -1;
        this.Owner = null;
        this.TypeId = -1;
        this.Name = null;
        this.Year = -1;
        this.Model = null;
    }
    
    public Tool(int id, String owner, int typeId, String name, int year, String model) {
        this.Id = id;
        this.Owner = owner;
        this.TypeId = typeId;
        this.Name = name;
        this.Year = year;
        this.Model = model;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        this.Owner = owner;
    }

    public int getTypeId() {
        return TypeId;
    }

    public void setTypeId(int typeId) {
        this.TypeId = typeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        this.Year = year;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        this.Model = model;
    }


    /*****************************************************************************
     * DB Functions
     *
     */

    // TOOL TABLE
    public static final String TABLE_TOOLS = "tools";
    public static final String TOOL_COLUMN_ID = "id";
    public static final String TOOL_COLUMN_OWNER = "owner";
    public static final String TOOL_COLUMN_TYPE_ID = "type_id";
    public static final String TOOL_COLUMN_NAME = "name";
    public static final String TOOL_COLUMN_YEAR = "year";
    public static final String TOOL_COLUMN_MODEL = "model";
}
