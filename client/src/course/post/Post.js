import { React } from 'react';
import './index.css';

const Post = ({ post }) => {

    // TODO save done to prevent refresh data loose

    const markPostAsChecked = () => {
        post.done = !post.done;
    }

    return (
        <div className="post-box">
            <p className="post-title">Hello from Post {post.title}!</p>
            <input className='post-checkbox' type="checkbox" id={post.id} 
                name="done" value={post.done} 
                onClick={markPostAsChecked} />
        </div>
    );
};

export default Post;
