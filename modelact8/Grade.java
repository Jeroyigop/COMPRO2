package com.corales.modell;

public class Grade {

    private String subject;
    private double prelims;
    private double midterms;
    private double finals;

    public Grade() {
    }

    public Grade(String subject, double prelims, double midterms, double finals) {
        this.subject = subject;
        this.prelims = prelims;
        this.midterms = midterms;
        this.finals = finals;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getPrelims() {
        return prelims;
    }

    public void setPrelims(double prelims) {
        this.prelims = prelims;
    }

    public double getMidterms() {
        return midterms;
    }

    public void setMidterms(double midterms) {
        this.midterms = midterms;
    }

    public double getFinals() {
        return finals;
    }

    public void setFinals(double finals) {
        this.finals = finals;
    }

}