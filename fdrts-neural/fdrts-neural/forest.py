import joblib

import numpy as np


class Forest:

    def __init__(self):
        self.model = joblib.load("./random_forest.joblib")

    def predict(self, input):
        f = np.array([[
            input["category"],
            input["amount"],
            input["lat"],
            input["long"],
            input["city_pop"],
            input["unix_time"],
            input["merch_lat"],
            input["merch_long"],
            input["trans_hour"],
            input["trans_day"],
            input["trans_month"],
        ]])


        res = self.model.predict(f)

        print(res)

        return res
