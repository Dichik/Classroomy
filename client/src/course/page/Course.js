import { React, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Post from '../post/Post';
import './index.css';

const Course = () => {

    const [loading, setLoading] = useState(false);
    const [posts, setPosts] = useState([]);
    const location = useLocation();
    const course = location.state.course;

    const loadPosts = () => {
        fetch(`http://localhost:8080/courses/${location.state.course.id}/posts`)
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                setPosts(data);
                setLoading(false);
            });
    };

    useEffect(() => {
        setLoading(true);
        loadPosts();
        setLoading(false);
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    const doSomething = () => {
        const requestOptions = {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(posts)
        };

        fetch(`http://localhost:8080/posts`, requestOptions)
            .then((response) => response.json())
            .then((data) => {
                setPosts(data);
                setLoading(false);
            });
    }

    const markPostAsChecked = (id) => {
        for (let i = 0; i < posts.length; i ++ ) {
            if (posts[i].id === id) {
                posts[i].done = !posts[i].done;
            }
        }
    }

    return (
        <div className="col course-details-page">
            <div className="sub-header">
                <h2 className="course-details-title">{course === null || course.name !== null ? course.name : 'loading course name...'}</h2>
                <div className="course-actions">
                    <label id="actions">choose an action: </label>

                    <select name="actions" id="actions" onChange={doSomething}>
                        <option value="no-action" defaultChecked>no action</option>
                        <option value="mark-as-done">mark as done</option>
                    </select>
                </div>
            </div>
            {posts.length !== 0 ? posts.map((post) => {
                return <Post key={post.id} post={post} markPostAsChecked={markPostAsChecked} />;
            }) : <h2 className='no-posts-title'>There are no posts for this course!</h2>}
        </div>
    );
};

export default Course;
