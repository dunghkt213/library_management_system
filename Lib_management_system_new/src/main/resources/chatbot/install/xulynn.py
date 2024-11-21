from flask import Flask, request, jsonify
from transformers import AutoTokenizer, AutoModelForQuestionAnswering
import torch

app = Flask(__name__)

# Load model and tokenizer
tokenizer = AutoTokenizer.from_pretrained("deepset/roberta-base-squad2")
model = AutoModelForQuestionAnswering.from_pretrained("deepset/roberta-base-squad2")

@app.route('/ask', methods=['POST'])
def ask_question():
    # Nhận câu hỏi và context từ yêu cầu
    data = request.json
    question = data.get('question', '')
    context = data.get('context', '')

    if not question or not context:
        return jsonify({'error': 'Cần cung cấp cả câu hỏi và ngữ cảnh!'}), 400

    # Tokenize câu hỏi và context
    inputs = tokenizer(question, context, return_tensors="pt")

    # Dự đoán câu trả lời
    outputs = model(**inputs)

    # Lấy logits bắt đầu và kết thúc
    start_logits = outputs.start_logits
    end_logits = outputs.end_logits

    # Tìm start và end index
    start_idx = torch.argmax(start_logits)
    end_idx = torch.argmax(end_logits)

    # Tạo câu trả lời
    answer = tokenizer.decode(inputs.input_ids[0][start_idx:end_idx + 1], skip_special_tokens=True)
    return jsonify({'answer': answer})

if __name__ == '__main__':
    app.run(debug=True)
