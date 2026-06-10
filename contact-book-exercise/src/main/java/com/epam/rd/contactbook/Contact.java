package com.epam.rd.contactbook;

import java.util.Objects;

public class Contact {

    private String name;
    private String phoneNumber;
    private String[] emails = new String[3];
    private int emailCount = 0;
    private String[] socialMedia = new String[5];
    private String[] socialMediaTitles = new String[5];
    private int socialMediaCount = 0;
    public Contact(String contactName) {
        rename(contactName);
    }

    public void rename(String newName) {
        if (newName == null || newName.isEmpty()) {
            return;
        }
        this.name = newName;
    }
    private class NameContactInfo implements ContactInfo {
        @Override
        public String getTitle() {
            return "Name";
        }

        @Override
        public String getValue() {
            return name;
        }
    }

    public static class Email implements ContactInfo {
        private String localPart;
        private String domain;

        public Email(String localPart, String domain) {
            this.localPart = localPart;
            this.domain = domain;
        }

        @Override
        public String getTitle() {
            if (domain.equals("epam.com")){
                return "Epam Email";
            }
            return "Email";
        }

        @Override
        public String getValue() {
            return localPart + "@" + domain;
        }
    }

    public static class Social implements ContactInfo {
        private String title;
        private String id;

        public Social(String title, String id) {
            this.title = title;
            this.id = id;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return id;
        }
    }


    public Email addEmail(String localPart, String domain) {
        if (emailCount < emails.length) {
            Email email = new Email(localPart, domain);
            emails[emailCount++] = email.getValue();
            return email;
        }
        return null;
    }


    public Email addEpamEmail(String firstname, String lastname) {
        if (emailCount < emails.length) {
            Email epamEmail = new Email(firstname + "_" + lastname, "epam.com") {
                @Override
                public String getTitle() {
                    return "Epam Email";
                }
            };
            emails[emailCount++] = epamEmail.getValue();
            return epamEmail;
        }
        return null;
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if (this.phoneNumber == null) {
            ContactInfo phoneInfo = new ContactInfo() {
                @Override
                public String getTitle() {
                    return "Tel";
                }

                @Override
                public String getValue() {
                    return "+" + code + " " + number;
                }
            };
            this.phoneNumber = phoneInfo.getValue();
            return phoneInfo;
        }
        return null;
    }

    public Social addTwitter(String twitterId) {
        return addSocialMedia("Twitter", twitterId);
    }

    public Social addInstagram(String instagramId) {
        return addSocialMedia("Instagram", instagramId);
    }

    public Social addSocialMedia(String title, String id) {
        if (socialMediaCount < socialMedia.length) {
            Social social = new Social(title, id);
            socialMedia[socialMediaCount] = social.getValue();
            socialMediaTitles[socialMediaCount++] = social.getTitle();
            return social;
        }
        return null;
    }

    public ContactInfo[] getInfo() {
        int size = 1; // For NameContactInfo
        if (phoneNumber != null) {
            size++;
        }
        size += emailCount;
        size += socialMediaCount;

        ContactInfo[] info = new ContactInfo[size];
        int index = 0;

        info[index++] = new NameContactInfo();

        if (phoneNumber != null) {
            info[index++] = new ContactInfo() {
                @Override
                public String getTitle() {
                    return "Tel";
                }

                @Override
                public String getValue() {
                    return phoneNumber;
                }
            };
        }

        for (int i = 0; i < emailCount; i++) {
            String[] parts = emails[i].split("@");
            info[index++] = new Email(parts[0], parts[1]);
        }

        for (int i = 0; i < socialMediaCount; i++) {
            info[index++] = new Social(socialMediaTitles[i], socialMedia[i]);
        }

        return info;
    }

}
