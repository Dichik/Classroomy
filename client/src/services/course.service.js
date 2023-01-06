import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/courses';

class CourseService {
    getCourses() {
        return axios.get(API_URL, { headers: authHeader() });
    }

    createCourse(name, description) {
        const course = {
            name: name,
            description: description
        };
        const headers = authHeader();
        const requestOptions = {
            method: "post",
            url: API_URL,
            headers: headers,
            data: JSON.stringify(course)
        };
        return axios(API_URL, requestOptions);
    }

    getPostsByCourseId(id) {
        return axios.get(API_URL + `?courseId=${id}`, {
            headers: authHeader()
        });
    }
}

export default new CourseService();
