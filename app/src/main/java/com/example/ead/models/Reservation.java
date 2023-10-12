package com.example.ead.models;

public class Reservation {

    private String _id;
    private String trainScheduleid;
    private String nic;
    private String createdAt;
    private String updatedAt;
    private String reservationDate;
    private int reserveCount;
    private int status;

    public Reservation() {}

    public Reservation(String _id, String trainScheduleid, String nic, String createdAt, String updatedAt, String reservationDate, int reserveCount, int status) {
        this._id = _id;
        this.trainScheduleid = trainScheduleid;
        this.nic = nic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reservationDate = reservationDate;
        this.reserveCount = reserveCount;
        this.status = status;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTrainScheduleid() {
        return trainScheduleid;
    }

    public void setTrainScheduleid(String trainScheduleid) {
        this.trainScheduleid = trainScheduleid;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public int getReserveCount() {
        return reserveCount;
    }

    public void setReserveCount(int reserveCount) {
        this.reserveCount = reserveCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
