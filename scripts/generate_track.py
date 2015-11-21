import requests
import io
import json
import compass_bearing

class GoogleRoadsJSON:

    #These points are just for testing purposes.
    points_test = (
        (12.921967, 77.695293),
        (12.921972, 77.693630),
        (12.922097, 77.691390),
        (12.922128, 77.689931),
        (12.922186, 77.688606),
        (12.922152, 77.687952),
        (12.920782, 77.687888),
        (12.920066, 77.687829)
    )

    points_test_2 = (
        (12.923104, 77.694599),
        (12.922241, 77.694551),
        (12.921964, 77.694546),
        (12.922037, 77.692438),
        (12.922068, 77.692277),
        (12.922126, 77.690507),
        (12.922141, 77.689379),
        (12.922193, 77.688446),
    )

    points_test_3 = (
        (12.923104, 77.694599),
        (12.922241, 77.694551),
        (12.921964, 77.694546),
        (12.922037, 77.692438),
        (12.922068, 77.692277),
        (12.922126, 77.690507),
        (12.922141, 77.689379),
        (12.922193, 77.688446),
        (12.922153, 77.687940),
        (12.920916, 77.687910),
        (12.920051, 77.687880),
    )

    fivek = (
        (12.920103, 77.686673), # start
        (12.920171, 77.685579),
        (12.920806, 77.685576), #sap turn
        (12.920836, 77.685596), #rmzloop begin
        (12.920810, 77.685258),
        (12.920878, 77.683659),
        (12.920575, 77.683670),
        (12.920512, 77.685193),
        (12.920810, 77.685258),
        (12.920836, 77.685596), #rmzloop end
        (12.920858, 77.683666),
        (12.920573, 77.683658),
        (12.920530, 77.685222),
        (12.920802, 77.685235),
        (12.920805, 77.685576),
        (12.926812, 77.685778),
        (12.927772, 77.684779), 
        (12.928844, 77.684720),# intel gate
        (12.927772, 77.684779), 
        (12.926812, 77.685778), 
        (12.920805, 77.685576),
        (12.920802, 77.685235),
        (12.920530, 77.685222),
        (12.920573, 77.683658),
        (12.920858, 77.683666),
        (12.920836, 77.685596), #rmzloop begin
        (12.920810, 77.685258),
        (12.920878, 77.683659),
        (12.920575, 77.683670),
        (12.920512, 77.685193),
        (12.920810, 77.685258),
        (12.920836, 77.685596), #rmzloop end
        (12.920806, 77.685576), #sap turn
        (12.920171, 77.685579),
        (12.920103, 77.686673), # start
        (12.920081, 77.687929), # T1 turn
        (12.922131, 77.687967), # Villa turn
        (12.922154, 77.689463), #Villa Lane 2 turn
        (12.922154, 77.689463), #Villa Lane 2 turn
        (12.922131, 77.687967), # Villa turn
        (12.920081, 77.687929), # T1 turn
        (12.920103, 77.686673), # start
    )

    #new stuff
    n_fivek = (
        (12.920103, 77.686673), # start
        (12.920171, 77.685579),
        (12.920806, 77.685576), #sap turn
        (12.920836, 77.685596), #rmzloop
        (12.922133, 77.685671),
        (12.923388, 77.685928),
        (12.924653, 77.685939),
        (12.925814, 77.685821),
        (12.926975, 77.685757),
        (12.927466, 77.685306),
        (12.927466, 77.685306),
        (12.927894, 77.684750),
        (12.928669, 77.684727), #intel gate
        (12.927894, 77.684750),
        (12.927466, 77.685306),
        (12.927466, 77.685306),
        (12.926975, 77.685757),
        (12.925814, 77.685821),
        (12.924653, 77.685939),
        (12.923388, 77.685928),
        (12.922133, 77.685671),
        (12.920836, 77.685596), #rmzloop
        (12.920806, 77.685576), #sap turn
        (12.920171, 77.685579),
        (12.920103, 77.686673), # start
        (12.920083, 77.687934),
        (12.922151, 77.687952),
        (12.922182, 77.688360),
        (12.922172, 77.688811),
        (12.922131, 77.690013),# lane 3
        (12.922172, 77.688811),
        (12.922182, 77.688360),
        (12.922151, 77.687952),
        (12.920083, 77.687934),
        (12.920103, 77.686673), # start
    )

    tenk = (fivek+fivek)

    # todo
    half_marathon = (
        (12.920128, 77.686778), #start
        (12.920201, 77.685560),
        (12.921790, 77.685619), #rmz
        (12.923359, 77.685919),
        (12.924938, 77.685930),
        (12.926067, 77.685801),
        (12.927060, 77.685737),
        (12.927468, 77.685340),
        (12.927719, 77.684857),
        (12.928021, 77.684727),
        (12.928596, 77.684726),
        (12.928021, 77.684727),
        (12.927719, 77.684857),
        (12.927468, 77.685340),
        (12.927060, 77.685737),
        (12.926067, 77.685801),
        (12.924938, 77.685930),
        (12.923359, 77.685919),
        (12.921790, 77.685619), #rmz
        (12.920180, 77.685555),
        (12.918768, 77.685448), #Bellandhur road
        (12.918486, 77.686199),
        (12.918308, 77.686510),
        (12.917942, 77.687315),
        (12.917796, 77.687701),
        (12.917074, 77.689267), #start of unknown road
        (12.916928, 77.689417),
        (12.915506, 77.689278),
        (12.915381, 77.689353),
        (12.915475, 77.691027),
        (12.915632, 77.692744),
        (12.915810, 77.694439),
        (12.916030, 77.696177),
        (12.915915, 77.697346),
        (12.915622, 77.698322),
        (12.915915, 77.697346),
        (12.916030, 77.696177),
        (12.915810, 77.694439),
        (12.915632, 77.692744),
        (12.915475, 77.691027),
        (12.915381, 77.689353),
        (12.915506, 77.689278),
        (12.916928, 77.689417),
        (12.917074, 77.689267), #start of unknown road
        (12.917231, 77.688967),
        (12.918695, 77.688999),
        (12.919793, 77.689042),
        (12.919709, 77.690780),
        (12.919615, 77.692411),
        (12.919552, 77.694160),
        (12.919552, 77.694407), #phase 3
        (12.917722, 77.694332),
        (12.917806, 77.692658),
        (12.917806, 77.692358),
        (12.916562, 77.692288),
        (12.917806, 77.692358),
        (12.917806, 77.692658),
        (12.917722, 77.694332),
        (12.919552, 77.694407), #phase 3
        (12.919385, 77.696124),
        (12.919343, 77.696736),
        (12.920525, 77.696157),
        (12.921330, 77.695556),
        (12.922010, 77.695406),
        (12.921947, 77.694709),
        (12.921989, 77.693121),
        (12.922083, 77.691630),
        (12.922156, 77.689892),
        (12.922208, 77.688476), #villa gate
        (12.922156, 77.687940),
        (12.920085, 77.687929),
        (12.920128, 77.686778), #start
    )

    twok = (
        (12.920008, 77.688594),
        (12.920081, 77.687929), # T1 turn
        (12.922131, 77.687967), # Villa turn
        (12.922115, 77.690579),
        (12.921927, 77.694710),
        (12.921927, 77.694710),
        (12.922115, 77.690579),
        (12.922131, 77.687967),
        (12.920081, 77.687929),
        (12.920008, 77.688594),
    )

    api_key = 'AIzaSyArZFq6YPIfBR_xfbTA1QD0uHLi6aPKSzU'

    def convert_points_to_string(self, input_points):
        response = ''
        for x in input_points:
            for y in x:
                response += str(y) + ','
            response = response[:-1]
            response += "|"
        response = response[:-1]
        return response
    
    def getJSON(self, points):

        params = {
            'path': self.convert_points_to_string(points),
            'interpolate':'true',
            'key': self.api_key,
        }
        response = requests.get("https://roads.googleapis.com/v1/snapToRoads?", params = params)
        return response.text

class StreamAverage:

    average = 0.0
    max_size = 10.0
    stream = []

    def push_number(self, number):

        if(len(self.stream) > self.max_size):
            del self.stream[0]
            self.stream += [number]

            self.average *= self.max_size
            self.average -= self.stream[0]
            self.average += number
            self.average /= self.max_size
        else:
            self.average *= len(self.stream)
            self.average += number
            self.stream += [number]
            self.average /= len(self.stream)

        return self.average

    def clear_stack(self):

        self.average = 0.0
        self.stream = []


class Turns:

    def insert_turns(self, jsontext):

        jsondump = json.loads(jsontext)
        snappedPoints = jsondump['snappedPoints']
        streamAverage = StreamAverage()
        limit = 30.0
        Points = []

        for i in xrange(len(snappedPoints) - 1):

            pointA = (snappedPoints[i]['location']['latitude'], snappedPoints[i]['location']['longitude'])
            pointB = (snappedPoints[i + 1]['location']['latitude'], snappedPoints[i + 1]['location']['longitude'])

            #check for redundancy
            if (pointA == pointB):
                continue
            
            heading = compass_bearing.calculate_compass_bearing(pointA, pointB)

            turn = 0
            if(len(streamAverage.stream) != 0):
                if(streamAverage.stream != None or streamAverage.stream != []):
                    if(heading > streamAverage.average + limit):
                        turn = 1
                        streamAverage.clear_stack()
                    elif(heading < streamAverage.average - limit):
                        turn = -1
                        streamAverage.clear_stack()

            streamAverage.push_number(heading)

            #insert heading into json file
            Points.append({'location':{'latitude':pointA[0], 'longitude':pointA[1]}, 'turn':turn})

        return json.dumps({'snappedPoints':Points}, indent=2, sort_keys=True)


roads = GoogleRoadsJSON()

def writeJsonTrack(track, file):
    jsontext = roads.getJSON(track)
    print jsontext
    turns = Turns()
    jsontext = turns.insert_turns(jsontext)
    jsonfile = open(file, 'w')
    jsonfile.write(jsontext)
    jsonfile.close()

writeJsonTrack(roads.half_marathon, "halfmarathon.json")
#writeJsonTrack(roads.tenk, "tenk.json")
#writeJsonTrack(roads.n_fivek, "new_fivek.json")
#writeJsonTrack(roads.twok, "twok.json")
