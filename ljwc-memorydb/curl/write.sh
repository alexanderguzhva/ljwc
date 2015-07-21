curl -H "Content-Type: application/json" -X POST -d '{"elements":[{"key":"meow","timestamp": 946786379001,"data":{"key1": "dmFsdWUx", "d":"dmFsdWUx"},"meta":{"keyA":"dmFsdWVh"}}]}' http://localhost:11010
curl -H "Content-Type: application/json" -X POST -d '{"elements":[{"key":"meow","timestamp": 946786379002,"data":{"key3": "dmFsdWUx", "d":"dmFsdWUy"},"meta":{"keyB":"dmFsdWVh"}}]}' http://localhost:11010
curl -H "Content-Type: application/json" -X POST -d '{"elements":[{"key":"meow8","timestamp": 946786379001,"data":{"key6": "dmFsdWUx"},"meta":{"keyC":"dmFsdWVh"}}]}' http://localhost:11010
