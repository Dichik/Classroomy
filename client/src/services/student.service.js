import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/students';

class StudentService {
    getStudentsByCourseId(courseId) {
        return axios.get(API_URL + `?courseId=${courseId}`, {
            headers: authHeader()
        });
    }
}

export default new StudentService();
