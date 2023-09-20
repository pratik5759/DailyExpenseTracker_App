package com.example.udaily;

public class TransactionModel {
    private String Amount,Id,Note,Type,date;
    private String Category;

    public TransactionModel(){

    }

    public TransactionModel(String Amount,String Category,String Id,String Note,String Type,String date){
        this.Amount=Amount;
        this.Category=Category;
        this.Id = Id;
        this.Note = Note;
        this.Type = Type;
        this.date=date;

    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        this.Amount = amount;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        this.Note = note;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
