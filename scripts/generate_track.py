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

    tenk = (fivek+fivek)

    # todo
    half_marathon = (fivek+fivek)

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
writeJsonTrack(roads.tenk, "tenk.json")
writeJsonTrack(roads.fivek, "fivek.json")
writeJsonTrack(roads.twok, "twok.json")
       
