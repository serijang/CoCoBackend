from datetime import datetime

from flask import Blueprint, current_app, jsonify, request

from presets.status import STATUS_CODE, STATUS_MESSAGE

import jwt

# Flask Blueprint 생성
bp = Blueprint('post', __name__, url_prefix='/post')


# 글 작성 라우터 정의
@bp.route('', methods=['POST'])
def write_post():
    token = request.cookies.get('token')

    if token == None:
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_TOKEN']
        }), STATUS_CODE['INVALID_TOKEN']

    payload = jwt.decode(token, current_app.jwt_secret_key, algorithms='HS256')

    if current_app.db.users.find_one({'id': payload['id']}) == None:
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_TOKEN']
        }), STATUS_CODE['INVALID_TOKEN']

    title = request.form.get('title')
    current_group = request.form.getlist('current_group[]')
    recruitment_fields = request.form.getlist('recruitment_fields[]')
    region = request.form.get('region')
    period = request.form.get('period')
    contact = request.form.get('contact')
    content = request.form.get('content')

    if title == None or title == '':
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_PARAM']('title')
        }), STATUS_CODE['INVALID_PARAM']

    if len(current_group) == 0:
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_PARAM']('current_group')
        }), STATUS_CODE['INVALID_PARAM']

    if len(recruitment_fields) == 0:
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_PARAM']('recruitment_fields')
        }), STATUS_CODE['INVALID_PARAM']

    if region == None or region == '':
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_PARAM']('region')
        }), STATUS_CODE['INVALID_PARAM']

    if period == None or period == '':
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_PARAM']('period')
        }), STATUS_CODE['INVALID_PARAM']

    if contact == None or contact == '':
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_PARAM']('contact')
        }), STATUS_CODE['INVALID_PARAM']

    if content == None or content == '':
        return jsonify({
            'status': STATUS_MESSAGE['INVALID_PARAM']('content')
        }), STATUS_CODE['INVALID_PARAM']

    doc = {
        'user_id': payload['id'],
        'title': title,
        'current_group': current_group,
        'recruitment_fields': recruitment_fields,
        'region': region,
        'period': period,
        'contact': contact,
        'content': content,
        'date': datetime.now(),
        'hits': 0,
        'recruitment_status': True
    }

    current_app.db.posts.insert_one(doc)

    return jsonify({
        'status': STATUS_MESSAGE['SUCCESS']
    }), STATUS_CODE['SUCCESS']
