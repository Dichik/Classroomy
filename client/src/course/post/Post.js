import { React } from 'react';
import './index.css';

const Post = ({ post, markPostAsChecked }) => {

    return (
        <div className="post-box">
            <p className="post-title">{post.title}!</p>
            <input className='post-checkbox' type="checkbox" id={post.id} 
                name="done" value={post.done} defaultValue={post.done}
                onClick={() => markPostAsChecked(post.id)} />
        </div>
    );
};

export default Post;
