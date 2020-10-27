package eci.ieti.data.model;

import java.util.Date;

public class Todo {
    String description, responsible, status;
    Date dueDate;
    int priority;
    public Todo(){

    }
    public Todo(String description, Date dueDate, String responsible, String status, int priority) {
        this.description = description;
        this.dueDate = dueDate;
        this.responsible = responsible;
        this.status = status;
        this.priority = priority;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public String getResponsible() {
        return responsible;
    }
    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", responsible='" + responsible + '\'' +
                ", status='" + status + '\'' +
                ", priority=" + priority +
                '}';
    }
}
