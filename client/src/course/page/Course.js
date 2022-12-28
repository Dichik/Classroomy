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
        name: 'algorithms',
        description: 'learn algorithms and become a better developer!'
    };

    const initPosts = [
        {
            id: 1,
            title: 'title1',
            description: 'description1',
            done: false
        },
        {
            id: 2,
            title: 'title2',
            description: 'description2',
            done: true
        }
    ];

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
        setPosts(initPosts);
        setLoading(false);
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    const doSomething = (e) => {
        let chosenOption = e.target.value;
        console.log(chosenOption);
        for (let i = 0; i < posts.length; i++ ) {
            if (posts[i].done == true) {
                initPosts[i] = true;
            }
        }
    }


    return (
        <div className="col course-details-page">
            <div className="sub-header">
                <h2 className="course-details-title">{initCourseDetails.name}</h2>
                <div className="course-actions">
                    <label id="actions">choose an action: </label>

                    <select name="actions" id="actions" onChange={doSomething}>
                        <option value="no-action" selected>no action</option>
                        <option value="mark-as-done">mark as done</option>
                    </select>
                </div>
            </div>
            {posts.map((post) => {
                return <Post key={post.id} post={post} />;
            })}
        </div>
    );
};

export default Course;
