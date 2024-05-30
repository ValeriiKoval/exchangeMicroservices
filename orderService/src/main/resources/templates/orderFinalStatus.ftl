<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Status Update</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            color: #333;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .content {
            line-height: 1.5;
        }

        .status {
            font-weight: bold;
            color: #28a745;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="content">
        <p>Dear Customer,</p>
        <p>We are pleased to inform you that your order #<span class="order-number">${orderId}</span> has reached its
            final status.</p>
        <p>Current order status: <span class="status">${status}</span></p>
    </div>
</div>
</body>
</html>