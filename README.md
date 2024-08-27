# Campaign Manager
## Overview
This project is a web application designed to manage marketing campaigns for products. It features a REST API backend built with Spring Boot and a frontend developed using React with TypeScript. Users can create, edit, and delete campaigns, as well as view and manage their campaign balance.

## Technologies
### Backend
- Spring Boot
- H2 Database
### Frontend
- React
- TypeScript

## Features
- Create, Edit, and Delete Campaigns: Manage individual marketing campaigns for products.
- Form Fields:
  - Campaign Name
  - Keywords (with typeahead functionality)
  - Bid Amount
  - Campaign Fund (deducted from a virtual balance)
  - Status (On/Off)
  - Town (pre-populated dropdown list)
  - Radius
- Balance Display: Shows the current balance and updates it based on the campaign fund used.
