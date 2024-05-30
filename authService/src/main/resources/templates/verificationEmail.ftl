<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Email Verification</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .content {
            line-height: 1.5;
        }

        .button {
            display: inline-block;
            background-color: #4CAF50;
            color: #fff;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="content">
        <h1>Verify Your Email Address</h1>
        <p>Thank you for signing up! Please click the button below to verify your email address:</p>
        <a href="${gatewayUrl}/verify?token=${token}" class="button">Verify Email</a>
        <p>If you did not sign up for our service, you can safely ignore this email.</p>
    </div>
</div>
</body>
</html>