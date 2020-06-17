import pytz
from locust import HttpUser, TaskSet, between, task
from datetime import datetime

USERTOKEN = ''
ADMINTOKEN = ''

organization_id = 1
place_id = 1

# Metodi di OrganizationApi e MovementApi da mettere sotto test (i più importanti)
#
# OrganizationApi
# Viene testata la lettura su SQL e la restituzione di molti dati
# Viene testata la scrittura tramite aggiornamento di dati (quindi cerco su db l'esistenza dell'organizzazione, verifico se l'area è corretta, leggo da db i limiti di area, scrivo su db)
#
# MovementApi
# Viene testata la pubblicazione su Redis Publisher/Subscriber e l'incremento di un valore di una coppia chiave-valore di Redis e la risposta con un exitToken generato casuale
# Viene testata la risposta del Subscriber (in ascolto del canale Publisher/Subscriber) che consiste nella lettura dal canale, deserializzazione, lettura da SQL, scrittura su SQL

tz = pytz.timezone('Europe/Rome')

class StalkerApi(TaskSet):
    entrance_movement_organization = {
        "timestamp": datetime.now(tz).strftime("%Y-%m-%dT%H:%M:%SZ"),
        "movementType": 1,
        "organizationId": organization_id
    }

    entrance_movement_place = {
        "timestamp": datetime.now(tz).strftime("%Y-%m-%dT%H:%M:%SZ"),
        "movementType": 1,
        "placeId": place_id
    }
	
    exit_movement_organization = {
        "movementType": -1,
        "organizationId": organization_id
    }

    exit_movement_place = {
        "movementType": -1,
        "placeId": place_id
    }

    organization = {
        "id": 1,
        "name": "",
        "description": "",
        "image": "",
        "street": "",
        "number": "",
        "postCode": 1,
        "city": "",
        "country": "",
        "creationDate": "",
        "lastChangeDate": "",
        "trackingArea": "{\"Organizzazioni\": [{}]}",
        "trackingMode": ""
    }

    @task
    def get_organization_list(self):
        self.client.get('/organization', headers = {'Authorization': 'Bearer ' + USERTOKEN})
		
    @task
    def get_administrator_list_organization(self):
        self.client.get('/administrator/organization/1', headers = {'Authorization': 'Bearer ' + ADMINTOKEN})

    @task
    def update_organization(self):
        self.client.put('/organization', headers = {'Authorization': 'Bearer ' + ADMINTOKEN}, json = self.organization)

    @task
    def track_movement_in_organization(self):
        r = self.client.post('/movement/track/organization', headers = {'Authorization': 'Bearer ' + USERTOKEN}, json = self.entrance_movement_organization)
        exitToken = r.json()['exitToken']
        self.exit_movement_organization['exitToken'] = exitToken
        self.exit_movement_organization['timestamp'] = datetime.now(tz).strftime("%Y-%m-%dT%H:%M:%SZ")
        self.client.post('/movement/track/organization', headers = {'Authorization': 'Bearer ' + USERTOKEN}, json = self.exit_movement_organization)
		
    @task
    def track_movement_in_place(self):
        r = self.client.post('/movement/track/place', headers = {'Authorization': 'Bearer ' + USERTOKEN}, json = self.entrance_movement_place)
        exitToken = r.json()['exitToken']
        self.exit_movement_place['exitToken'] = exitToken
        self.exit_movement_place['timestamp'] = datetime.now(tz).strftime("%Y-%m-%dT%H:%M:%SZ")
        self.client.post('/movement/track/place', headers = {'Authorization': 'Bearer ' + USERTOKEN}, json = self.exit_movement_place)
		
class QuickstartUser(HttpUser):
    tasks = [StalkerApi]
    wait_time = between(5, 15)
