from flask import Flask, request, jsonify
import joblib
import pandas as pd
from sklearn.preprocessing import StandardScaler

app = Flask(__name__)

# Load the trained model
loaded_model = joblib.load('model/random_forest_model.pkl')
time_model = joblib.load('model/time.pkl')


def predict_risk_details(input_data):
    # Predict risk level using the trained model
    predicted_risk = loaded_model.predict(input_data)
    x_columns = ['Domain', 'Mobile', 'Desktop', 'Web', 'IoT',
       'Days for Expected Timeline', 'Predicted Days to Deadline',
       'Expected Team Size', 'Expected Budget']

    # Identify factors causing the risk for each prediction
    factors = []
    for i, input_row in enumerate(input_data):
        importance_scores = loaded_model.feature_importances_
        factor_importance = dict(zip(x_columns, importance_scores))

        # Convert input row to binary representation
        binary_input_row = [1 if val else 0 for val in input_row]

        # Filter out the features based on the input data for each prediction
        factors_for_prediction = {x_columns[j]: factor_importance[x_columns[j]] for j, val in
                                  enumerate(binary_input_row) if val}
        factors.append(factors_for_prediction)

    return predicted_risk, factors

@app.route('/risk/predict', methods=['POST'])
def predict():
    data = request.get_json()  # Get JSON data from request
    sample_input = data['input']  # Extract input data from JSON

    # Predict using the loaded model
    predicted_risk, factors = predict_risk_details(sample_input)
    for i, risk in enumerate(predicted_risk):
        print(f"Prediction {i + 1}:")
        print("Predicted Risk Level:", risk)
        print("Factors Contributing to the Risk:")
        for feature, importance in factors[i].items():
            print(f"- {feature}: {importance}")
        print("\n")
    risk = predicted_risk[0]
    riskStatus = ""
    if risk == 1 :
        riskStatus = "low risk"
    elif risk == 2:
        riskStatus = "Average risk"
    else :
        riskStatus = "high risk"

    # Return the predicted risk as JSON response
    return jsonify({'predicted_risk': riskStatus ,"factors": factors})

# Define a scaler to scale new input data
scaler = StandardScaler()

@app.route('/time/predict', methods=['POST'])
def predict_time():
    # Get data from request
    data = request.json

    # Make prediction
    predicted_days = time_model.predict([data['features']])[0]

    # Prepare response
    response = {'predicted_days': predicted_days}

    return jsonify(response)

if __name__ == '__main__':
    app.run(debug=True)
