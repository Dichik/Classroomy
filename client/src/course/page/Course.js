import { React, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import PostPreview from '../post/PostPreview';
import './index.css';

export default function Course() {

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

    // const doSomething = () => {
    //     const requestOptions = {
    //         method: 'PUT',
    //         headers: { 'Content-Type': 'application/json' },
    //         body: JSON.stringify(posts)
    //     };

    //     fetch(`http://localhost:8080/posts`, requestOptions)
    //         .then((response) => response.json())
    //         .then((data) => {
    //             setPosts(data);
    //             setLoading(false);
    //         });
    // }

    return (
        <div className="course-details-page">
            <div className="sub-header">
                <h2 className="course-details-title">{
                    course === null || course.name !== null ? course.name : 'loading course name...'
                }</h2>
                {/* <div className="course-actions">
                    <label id="actions">choose an action: </label>

                    <select name="actions" id="actions" onChange={doSomething}>
                        <option value="no-action" defaultChecked>no action</option>
                        <option value="mark-as-done">mark as done</option>
                    </select>
                </div> */}
            </div>
            <div>
                <input id='title' name='title' className='title-input' type='text' />
                <input id='description' name='description' className='description-input' type='text' />
                <button className='submit-post-button'>create post</button>
            </div>
            {posts.length !== 0 ? posts.map((post) => {
                return <PostPreview key={post.id} post={post} />;
            }) : <h2 className='no-posts-title'>There are no posts for this course!</h2>}
        </div>
    );
}