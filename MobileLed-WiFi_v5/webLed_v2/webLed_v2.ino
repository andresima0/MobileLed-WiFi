#include <WiFi.h>
#include <WebServer.h>

WebServer server(80);

const char* ssid ="XXXXX";      // Replace with your Wi-Fi network name (SSID)
const char* password = "XXXXX"; // Replace with your Wi-Fi network password

String led1On = "<a href=\"/Led01_Off\"><button>Turn LED 1 OFF</button></a>";
String led1Off = "<a href=\"/Led01_On\"><button>Turn LED 1 ON</button></a>";

String led2On = "<a href=\"/Led02_Off\"><button>Turn LED 2 OFF</button></a>";
String led2Off = "<a href=\"/Led02_On\"><button>Turn LED 2 ON</button></a>";

String led3On = "<a href=\"/Led03_Off\"><button>Turn LED 3 OFF</button></a>";
String led3Off = "<a href=\"/Led03_On\"><button>Turn LED 3 ON</button></a>";


int led01Pin = 18;
int led02Pin = 19;
int led03Pin = 21;

void Led1On() {
  digitalWrite(led01Pin, HIGH);
  server.send(200, "text/html", led1On);
}

void Led1Off() {
  digitalWrite(led01Pin, LOW);
  server.send(200, "text/html", led1Off);
}

void Led2On() {
  digitalWrite(led02Pin, HIGH);
  server.send(200, "text/html", led2On);
}

void Led2Off() {
  digitalWrite(led02Pin, LOW);
  server.send(200, "text/html", led2Off);
}

void Led3On() {
  digitalWrite(led03Pin, HIGH);
  server.send(200, "text/html", led1On);
}

void Led3Off() {
  digitalWrite(led03Pin, LOW);
  server.send(200, "text/html", led1Off);
}

void setup() {
  Serial.begin(115200);
  WiFi.mode(WIFI_STA);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED)
    delay(500);

  Serial.print("Local IP address: ");
  Serial.println(WiFi.localIP());

  server.on("/Led01_On", Led1On);
  server.on("/Led01_Off", Led1Off);

  server.on("/Led02_On", Led2On);
  server.on("/Led02_Off", Led2Off);

  server.on("/Led03_On", Led3On);
  server.on("/Led03_Off", Led3Off);

  server.begin();

  pinMode(led01Pin, OUTPUT);
  pinMode(led02Pin, OUTPUT);
  pinMode(led03Pin, OUTPUT);

  digitalWrite(led01Pin, LOW);
  digitalWrite(led02Pin, LOW);
  digitalWrite(led03Pin, LOW);
}

void loop() {
  server.handleClient();
  delay(1);
}