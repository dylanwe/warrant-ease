# warrant-ease!
![warrant-ease](https://github.com/dylanwe/warrant-ease/assets/41340521/ad84c3b1-08cb-4e1c-9f3b-558eb8cbec1c)

Say goodbye to the hassle of losing important warranty information, missing deadlines, or not knowing which products are still covered. With my app, you can easily keep track of the expiration dates for all your warranties in one place.

## Features
-   Login with Google or Email.
-   Add warranties for a product with the following information:
    -   Title
    -   Price
    -   What store it's bought at
    -   When you bought it
    -   When it's going to expire
    -   Notes
-   An overview of all your warranties
-   Edit warranty information
-   Deleting warranties


## Setup
### API setup
1. Create a MySQL schema with the name "warrant_ease".

2. Add environment variables to where you run the API to connect to your database:
```dotenv
JWT_ISSUER_URI=https://securetoken.google.com/<your firebase project id> // replace <> with your firebase project id
JWT_SET_URI=https://www.googleapis.com/service_accounts/v1/jwk/securetoken@system.gserviceaccount.com

MYSQL_DB="warrant_ease"
MYSQL_USERNAME="" // your db username
MYSQL_PASSWORD="" // your db password
```
3. Make sure to install all gradle dependencies

### Android setup
1. Make sure to install all gradle dependencies

### Google firebase auth
1. Go to [Firebase](https://firebase.google.com/)
2. Add a new project
3. Add the android app to this Firebase project
4. Navigate to build and setup authentication
   - Add Google and Email as a sign in method
5. Generate a SHA certificate as the [accepted answer](https://stackoverflow.com/questions/27609442/how-to-get-the-sha-1-fingerprint-certificate-in-android-studio-for-debug-mode) shows you
6. Add the SHA certificate fingerprint to your Firebase android app
7. Download the `google-servives.json`
8. Add google-services.json to the `android_app/app directory`
