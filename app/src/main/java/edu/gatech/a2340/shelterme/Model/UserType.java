package edu.gatech.a2340.shelterme.Model;

public enum UserType {
    USER,
    SHELTER_OWNER,
    ADMIN;

    /**
     * Formats the enum's default name, making it properly capitalized and
     * replacing underscores with spaces
     * @return formatted string
     */
    @Override
    public String toString() {
        String name = this.name().substring(0,1);
        if (this.name().length() > 1) {
            for (int i = 1; i < this.name().length(); i++) {
                String charAt = this.name().substring(i, i + 1);
                if (charAt.equals("_"))
                    charAt = " ";
                name += charAt.toLowerCase();
            }
        }
        return name;
    }
}
