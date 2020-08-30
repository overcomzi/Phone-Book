public class Phone {
    private String number, fullName;

    public Phone(String number, String fullName) {
        this.number = number;
        this.fullName = fullName;
    }

    public Phone(Phone phone) {
       number = phone.number;
       fullName = phone.fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNumber() {
        return number;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
