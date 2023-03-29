# YAYOAPP
Mobile app for the elderly people. Done in 36 hours for the UDC Hackton

<img src="https://d112y698adiu2z.cloudfront.net/photos/production/software_photos/002/397/960/datas/original.jpg"  width="300" height="300"><img src="https://d112y698adiu2z.cloudfront.net/photos/production/software_photos/002/397/958/datas/original.jpg"  width="300" height="300">

# HOW TO SETUP

To run the aplication, you need two API Keys: one for OpenWeatherAPI and another for OpenAI (this keys are used to create weather forecasts):
+ **OpenWeatherAPI**: You can get a free key [here](https://openweathermap.org/price)
+ **OpenAI**: You need an OpenAI account to create a key. In March 2023, you need to use a paid account to use this API (but for experimental uses, the costs are really cheap, about 20 cents were spent to develop this app). You can get more information [here](https://openai.com/blog/openai-api).

Once you have the necessary keys, you must enter them in the file **apikeys.properties**.

## Inspiration
Today, many older people live alone, making them more susceptible to situations such as forgetting to take their medicine or needing urgent medical help. This could be improved through the use of technology, but there are often barriers that prevent this:

Very rapid changes in technologies (smartphones are just over 15 years old).
Interfaces not designed for older people (small font sizes, very abrupt movements...).
This means that one of the groups that could most benefit from these advances cannot use them. YAYO APP aims to do its bit to advance in this area.

## What it does
YAYO APP is an Android application that implements, through a minimalist and intuitive interface, various functionalities that can help the elderly in their daily lives:

+ **Medication management**: Allows you to set reminders for taking medication, as well as to keep track of which ones have already been taken.
+ **Telephone**: An accessible interface with images makes it very easy to call the people closest to you.
+ **Weather**: Using the device's location, the sky and temperature are displayed. In addition, clothing recommendations for that specific weather are provided, using easy-to-understand natural language. In addition, all this information is read aloud so that it is not necessary to look at the device to know the forecast.
+ **Conversational assistant**: Using the ChatGPT language model, it incorporates a chat with an assistant, whose personality is intended to encourage the person. In addition, the bot's responses are read aloud to facilitate communication.

It should be noted that these functionalities are designed in a minimalist way, seeking to reduce external dependencies and data consumption as much as possible. In addition, the idea is that the app can be installed on older android devices (looking for compatibility with those systems), giving them a new life.

## How we built it
The application is programmed in Android Studio using the Java language. For the assistant part, the official ChatGPT API (from OpenAI) is used, as well as the Volley library to make the requests. To obtain the weather, the OpenWeather API is used.

## Challenges we face
The main challenge we faced was the conversational assistant. During the initial development of the app, there was no public API for the ChatGPT model, owned by OpenAI. 

Due to this major limitation, it was decided to use the official GPT3 API, which limits the wizard's possibilities but ensures its permanence over time (since using a connection to ChatGPT right now does not ensure that the application will be usable tomorrow).

However, a few days later the API was launched, so it was decided to redo the wizard to incorporate it.

In addition to this, the fact that there were only 3 members in the group, and that only one of them had worked with AndroidStudio before, caused us some difficulties at the beginning, but soon we were all able to get into the same rhythm.

## Achievements
Although the hackathon only lasted two days, we are very proud to have been able to develop a fully functional mobile application in that time. Moreover, it is designed to be as usable as possible by anyone, using recognisable icons and large texts; voice dictation in the assistant; neutral colour palette (black and white) to facilitate its use by visually impaired people and reduce data usage.

## What we have learned
Apart from learning new technologies such as AndroidStudio or the handling of APIs of different types, we have learned to work better as a team (despite already knowing each other before). Moreover, seeing ourselves in the situation of having to design, program and test an idea in just two days, has allowed us to distinguish better what things are important, how to manage time better, etc.

## Future improvements
The main improvement of YAYO App would be the inclusion of ChatGPT as an assistant, but this cannot be done reliably until the API is released by OpenAI. In addition, ideas such as bringing text reading to all screens, or implementing more functionalities in medication management were left in the pipeline.

The main idea of this application (and which was the starting point at the beginning of the design) is its future integration with a remote control system so that relatives can configure any part of the app remotely and can see the type of conversations they are having with the assistant or whether they have taken their medication for the day.
