import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import Post from '../post/Post';
import './index.css';

const Course = () => {
    const params = useParams();

    const [loading, setLoading] = useState(false);
    const [courseDetails, setCourseDatails] = useState(null);
    const [posts, setPosts] = useState([]);

    const initCourseDetails = {
        id: params.id,
        name: 'course.name',
        description: 'course.description'
    };

    useEffect(() => {
        setLoading(true);

        // fetch('/courses/{course.id}')
        //     .then((response) => response.json())
        //     .then((data) => {
        //         setCourses(data);
        //         setLoading(false);
        //     });
        console.log(params.id);
        setCourseDatails(initCourseDetails);
        console.log(courseDetails);
        setPosts([
            {
                "id": 1,
                "title": "title1",
                "description": "description1"
            },
            {
                "id": 2,
                "title": "title2",
                "description": "description2"
            }
        ])
        setLoading(false);
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="course-details-page">
            <h2 className='course-details-title'>{initCourseDetails.name}</h2>
            {posts.map(post => {
                return <Post key={post.id} post={post}/>
            })}
        </div>
    );
};

export default Course;
