# PPKWU_4

This REST API converts data passed in XML, JSON, TXT and CSV format to XML, JSON, TXT and CSV.

Example input data;

TXT 
```
lowerCase: 4
upperCase: 5
numbers: 3
specialCharacters: 7
```

CSV 

```
lowerCase,upperCase,numbers,specialCharacters
4,5,3,7
```

JSON
```
{
    "upperCase": "5",
    "lowerCase": "4",
    "numbers": "3",
    "specialCharacters": "7"
}
```
XML
```
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<stringStats>
    <lowerCase>4</lowerCase>
    <upperCase>5</upperCase>
    <numbers>3</numbers>
    <specialCharacters>7</specialCharacters>
</stringStats>
```

TXT -> CSV

```
Request:

http://localhost:8082/format_controller/format_data/txt/csv/lowerCase: 4
upperCase: 5
numbers: 3
specialCharacters: 7


Response:

lowerCase,upperCase,numbers,specialCharacters
4,5,3,7
```

JSON -> XML
 
```
Request:

http://localhost:8082/format_controller/format_data/json/xml/{"upperCase":5,"lowerCase":4,"numbers":3,"specialCharacters":5}


Response:

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<stringStats>
    <lowerCase>4</lowerCase>
    <upperCase>5</upperCase>
    <numbers>3</numbers>
    <specialCharacters>5</specialCharacters>
</stringStats>
```


Poprawka zadania 3
This REST API provides you with informations about string you passed, in CSV, JSON and XML format and convert


CSV:

```
Request:

http://localhost:8082/response_format_controller/analyze_string/csv/fdasFDSAF764**)(())

Response:

lowerCase,upperCase,numbers,specialCharacters
4,5,3,7
```


JSON:
```
Request:

http://localhost:8082/response_format_controller/analyze_string/json/fdasFDSAF764**)(())

Response:

{
    "lowerCase": "4",
    "upperCase": "5",
    "numbers": "3",
    "specialCharacters": "7"
}
```

XML:
```
Request:
http://localhost:8082/response_format_controller/analyze_string/xml/fdasFDSAF764**)(())

Response:

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<stringStats>
    <lowerCase>4</lowerCase>
    <upperCase>5</upperCase>
    <numbers>3</numbers>
    <specialCharacters>7</specialCharacters>
</stringStats>
```

TXT:
```
Request:
http://localhost:8082/response_format_controller/analyze_string/txt/fdasFDSAF764**)(())

Response:

lowerCase: 4
upperCase: 5
numbers: 3
specialCharacters: 7
```
