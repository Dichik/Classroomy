import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/posts';

class PostService {
    getAllPostsByCourseId(id) {
        return axios.get(API_URL + `?courseId=${id}`, {
            headers: authHeader()
        });
    }

    getDeadlines = () => {
        return axios.get(API_URL + '/deadlines', { headers: authHeader() });
    };

    getPostById = (courseId, id) => {
        return axios.get(API_URL + `/${id}?courseId=${courseId}`, { headers: authHeader()});
    };

    updateAnswerToPost = (id, answer) => {
        const body = {
            answer: answer
        };
        const requestOptions = {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                Accept: 'application/json',
                authHeader
            },
            body: JSON.stringify(body)
        };
        return axios.patch(`http://localhost:8080/posts/${id}`, requestOptions);
    };
}

export default new PostService();
