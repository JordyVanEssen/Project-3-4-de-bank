/*
 *
 * All the resources for this project: https://www.hackster.io/Aritro
 * Modified by Aritro Mukherjee
 *
 *
 */

#include <Keypad.h>
#include <SPI.h>
#include <MFRC522.h>

#define LEDacces A1
#define LEDdenied A2
#define LEDpassAccept A3

#define SS_PIN 10
#define RST_PIN 9
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.

const byte ROWS = 4;
const byte COLS = 4;

char customKey;
char hexaKeys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

String passwordUser1 = "1234";
String passwordUser2 = "ABCD";

String enteredPassword = "";

byte rowPins[ROWS] = { 8, 7, 6, 5 };
byte colPins[COLS] = { 4, 3, 2, A0 };

Keypad customKeypad = Keypad(makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS);

void setup()
{
	pinMode(LEDacces, OUTPUT);
	pinMode(LEDdenied, OUTPUT);
	pinMode(LEDpassAccept, OUTPUT);


	Serial.begin(9600);   // Initiate a serial communication
	SPI.begin();      // Initiate  SPI bus
	mfrc522.PCD_Init();   // Initiate MFRC522
	Serial.println("Approximate your card to the reader...");
	Serial.println();

}

void turnOffLed(int d) {
	delay(d);

	digitalWrite(LEDacces, LOW);
	digitalWrite(LEDdenied, LOW);
	digitalWrite(LEDpassAccept, LOW);

}

void loop()
{
	// Look for new cards
	if (!mfrc522.PICC_IsNewCardPresent())
	{
		return;
	}
	// Select one of the cards
	if (!mfrc522.PICC_ReadCardSerial())
	{
		return;
	}
	//Show UID on serial monitor
	Serial.print("UID tag :");
	String content = "";
	byte letter;
	for (byte i = 0; i < mfrc522.uid.size; i++)//leest de tag en print het UID
	{
		Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
		Serial.print(mfrc522.uid.uidByte[i], HEX);
		content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
		content.concat(String(mfrc522.uid.uidByte[i], HEX));
	}
	Serial.println();
	Serial.print("Message : ");
	content.toUpperCase();

	if (content.substring(1) == "96 7E DB D9" || content.substring(1) == "60 20 9C A5") //als de tag deze UID code heeft
	{
		turnOffLed(0);
		digitalWrite(LEDpassAccept, HIGH);
		turnOffLed(500);

		Serial.println("Enter password: ");
		enteredPassword = "";

		if (content.substring(1) == "96 7E DB D9") {//user 1
			for (int i = 0; i < passwordUser1.length(); i++)//vraag een key op voor de lengte van het wachtwoord
			{
				customKey = customKeypad.waitForKey();

				if (customKey)
				{
					enteredPassword += customKey;//'plakt' de ingevoerde key aan het ingevoerde wachtwoord
				}
			}

			if (enteredPassword == passwordUser1)//als het goede wachtwoord is ingevoerd
			{
				turnOffLed(0);
				Serial.println("Authorized access");
				digitalWrite(LEDacces, HIGH);
			}
			else {
				turnOffLed(0);
				Serial.println("Invalid password...");
				digitalWrite(LEDdenied, HIGH);
			}
		}
		else if (content.substring(1) == "60 20 9C A5") {//user 2
			for (int i = 0; i < passwordUser2.length(); i++)
			{
				customKey = customKeypad.waitForKey();

				if (customKey)
				{
					enteredPassword += customKey;
				}
			}

			if (enteredPassword == passwordUser2)
			{
				turnOffLed(0);
				Serial.println("Authorized access");
				digitalWrite(LEDacces, HIGH);
			}
			else {
				turnOffLed(0);
				Serial.println("Invalid password...");
				digitalWrite(LEDdenied, HIGH);
			}
		}
		
		Serial.println();
		turnOffLed(1500);
	}
	else {//als de kaart niet toegevoegd is
		Serial.println(" Access denied");
		digitalWrite(LEDdenied, HIGH);
		turnOffLed(1500);
	}
}