from flask import Flask
from flask_restful import Api, Resource, reqparse
from forest import Forest

app = Flask(__name__)

api = Api()

parser = reqparse.RequestParser()
parser.add_argument("category", type=int)
parser.add_argument("amount", type=float)
parser.add_argument("lat", type=float)
parser.add_argument("long", type=float)
parser.add_argument("city_pop", type=int)
parser.add_argument("unix_time", type=int)
parser.add_argument("merch_lat", type=float)
parser.add_argument("merch_long", type=float)
parser.add_argument("trans_hour", type=int)
parser.add_argument("trans_day", type=int)
parser.add_argument("trans_month", type=int)

# col = category    amt lat long    city_pop    unix_time   merch_lat   merch_long

# f = np.array([[1, 800, 36.9946, -81.7266, 885, 1325620275, 36.7658904, -81.951839]]) trans_hour	trans_day	trans_month
#f = np.array([[1, 2500, 36.9946, -81.7266, 885, 1325620275, 36.7658904, -81.951839,23, 1 ,1]])
# {
#     "category" : 1,
#     "amount" : 800,
#     "lat" : 36.9946,
#     "long" : -81.7266,
#     "city_pop" : 885,
#     "unix_time" : 1325620275,
#     "merch_lat" : 36.7658904,
#     "merch_long": -81.951839,
#     "trans_hour" : 1,
#     "trans_day" : 0,
#     "trans_month" : 0,
# }
forest = Forest()


class NetworkController(Resource):
    def post(self):
        a = parser.parse_args()
        print(a)

        return {"is_fraud": forest.predict(a).tolist()[0]}


api.add_resource(NetworkController, "/api/network")
api.init_app(app)

if __name__ == "__main__":
    app.run(debug=True, port=8082, host="0.0.0.0")
