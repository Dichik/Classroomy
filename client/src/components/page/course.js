import React, { useEffect, useState } from 'react';
import DatePicker from 'react-date-picker';
import { useLocation, useNavigate } from 'react-router-dom';
import Spinner from '../spinner/spinner.component';
import authHeader from '../../services/auth-header';
import authService from '../../services/auth.service';
import postService from '../../services/post.service';
import PostPreview from '../post/PostPreview';

export default function Course() {
    const [loading, setLoading] = useState(false);
    const [posts, setPosts] = useState([]);
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [err, setErr] = useState('');
    const [deadline, setDeadline] = useState(new Date());
    const [showEnrollmentKey, setShowEnrollmentKey] = useState(false);

    const location = useLocation();
    const course = location.state.course;
    const navigate = useNavigate();

    useEffect(() => {
        setLoading(true);
        postService
            .getAllPostsByCourseId(course.id)
            .then((response) => response.data)
            .then((data) => {
                setPosts(data);
                setLoading(false);
            });

        const user = authService.getCurrentUser();
        if (user) {
            setShowEnrollmentKey(user.roles.includes('ROLE_TEACHER'));
        }
        setLoading(false);
    }, []);

    if (loading) {
        return <Spinner />;
    }

    const handlePostSubmit = async () => {
        setLoading(true);
        const post = {
            title: title,
            description: description,
            courseId: course.id,
            deadline: deadline
        };

        const requestOptions = {
            method: 'POST',
            headers: authHeader(),
            body: JSON.stringify(post)
        };
        try {
            const response = await fetch(
                `http://localhost:8080/api/posts`,
                requestOptions
            );
            if (!response.ok) {
                throw new Error(`Error! status: ${response.status}`);
            }

            const data = await response.json();
            console.log('result is', data);
            window.location.reload();
        } catch (err) {
            setErr(err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleBackClick = () => {
        navigate(-1);
    };

    return (
        <div>
            {err && (
                <h2
                    style={{
                        color: 'red'
                    }}
                >
                    {err}
                </h2>
            )}{' '}
            {/* TODO add popup window with the error */}
            <div className="course-details-page">
                <div className="sub-header">
                    <h2 className="course-details-title">{course.name}</h2>
                    {showEnrollmentKey && (
                        <h3>
                            <label>
                                Enrollment Key:{' '}
                                {course.enrollmentKey
                                    ? course.enrollmentKey
                                    : 'No enrollment key'}
                            </label>
                        </h3>
                    )}
                    {/* <div className="course-actions">
                        <label id="actions">choose an action: </label>

                        <select name="actions" id="actions" onChange={doSomething}>
                            <option value="no-action" defaultChecked>no action</option>
                            <option value="mark-as-done">mark as done</option>
                        </select>
                    </div> */}
                    <div className="course-details-back-button">
                        <button
                            className="cdb-button-style"
                            onClick={handleBackClick}
                        >
                            back
                        </button>
                    </div>
                </div>
                <form id="course-create" className="course-create">
                    <input
                        id="title"
                        name="title"
                        className="title-input"
                        type="text"
                        placeholder="title"
                        onChange={(e) => setTitle(e.target.value)}
                    />
                    <input
                        id="description"
                        name="description"
                        className="description-input"
                        type="text"
                        placeholder="description"
                        onChange={(e) => setDescription(e.target.value)}
                    />
                    <DatePicker
                        className="date-picker"
                        onChange={setDeadline}
                        value={deadline}
                    />
                    <button
                        className="submit-post-button"
                        onClick={handlePostSubmit}
                    >
                        create post
                    </button>
                </form>
                {posts.length !== 0 ? (
                    posts.map((post) => {
                        return (
                            <PostPreview
                                key={post.id}
                                courseId={course.id}
                                post={post}
                            />
                        );
                    })
                ) : (
                    <h2 className="no-posts-title">
                        There are no posts for this course!
                    </h2>
                )}
            </div>
        </div>
    );
}

// export default class Course extends Component {
//     constructor(props) {
//         super(props);

//         this.onPostsChange = this.onPostsChange.bind(this);
//         this.onTitleChange = this.onTitleChange.bind(this);
//         this.onDescriptionChange = this.onDescriptionChange.bind(this);
//         this.onDeadlineChange = this.onDeadlineChange.bind(this);
//         this.onLoadingChange = this.onLoadingChange.bind(this);
//         this.onErrChange = this.onErrChange.bind(this);

//         const course = this.props;
//         console.log(course)
//         console.log("here")
//         this.state = {
//             course: this.props,
//             posts: [],
//             title: '',
//             description: '',
//             err: '',
//             deadline: new Date(),
//             loading: false
//         };
//     }

//     onPostsChange(posts) {
//         this.setState({
//             posts: posts
//         });
//     }

//     onTitleChange(e) {
//         this.setState({
//             title: e.target.value
//         });
//     }

//     onDescriptionChange(e) {
//         this.setState({
//             description: e.target.value
//         });
//     }

//     onDeadlineChange(e) {
//         console.log(e);
//         this.setState({
//             deadline: e.target.value
//         });
//     }

//     onLoadingChange() {
//         this.setState({
//             loading: !this.state.loading
//         });
//     }

//     onErrChange(message) {
//         this.state.setState({
//             err: message
//         });
//     }

//     getAllPosts = () => {};

//     componentDidMount() {
//         this.onLoadingChange;
//         courseService
//             .getPostsByCourseId(this.state.course.id)
//             .then((response) => response.json())
//             .then((data) => {
//                 this.onPostsChange(data);
//                 this.onLoadingChange;
//             });
//     }

//     handlePostSubmit = async () => {
//         // setLoading(true);
//         // const post = {
//         //     title: title,
//         //     description: description,
//         //     courseId: course.id,
//         //     deadline: deadline
//         // };
//         // const requestOptions = {
//         //     method: 'POST',
//         //     headers: {
//         //         'Content-Type': 'application/json',
//         //         Accept: 'application/json'
//         //     },
//         //     body: JSON.stringify(post)
//         // };
//         // try {
//         //     const response = await fetch(
//         //         `http://localhost:8080/api/posts`,
//         //         requestOptions
//         //     );
//         //     if (!response.ok) {
//         //         throw new Error(`Error! status: ${response.status}`);
//         //     }

//         //     const data = await response.json();
//         //     posts.push(data);
//         //     console.log('result is', data);
//         // } catch (err) {
//         //     setErr(err.message);
//         // } finally {
//         //     setLoading(false);
//         // }
//     };

//     render() {

//         const course = this.state.course;
//         const err = this.state.err;
//         const posts = this.state.posts;

//         return (
//             <div>
//                 {this.state.loading ? (
//                     <Spinner />
//                 ) : (
//                     <div>
//                         {err && <h2>{err}</h2>}{' '}
//                         {/* TODO add popup window with the error */}
//                         <div>
//                             <div>
//                                 <h2>
//                                     {course === null || course.name !== null
//                                         ? course.name
//                                         : 'loading course name...'}
//                                 </h2>
//                                 {/* <div className="course-actions">
//                                     <label id="actions">choose an action: </label>

//                                     <select name="actions" id="actions" onChange={doSomething}>
//                                         <option value="no-action" defaultChecked>no action</option>
//                                         <option value="mark-as-done">mark as done</option>
//                                     </select>
//                                 </div> */}
//                                 <div>
//                                     <button
//                                         onClick={() => this.navigate(-1)}
//                                     >
//                                         back
//                                     </button>
//                                 </div>
//                             </div>
//                             <form id="course-create">
//                                 <input
//                                     id="title"
//                                     name="title"
//                                     type="text"
//                                     placeholder="title"
//                                     value={this.state.title}
//                                     onChange={this.onTitleChange}
//                                 />
//                                 <input
//                                     id="description"
//                                     name="description"
//                                     type="text"
//                                     placeholder="description"
//                                     value={this.state.description}
//                                     onChange={this.onDescriptionChange}
//                                 />
//                                 <DatePicker
//                                     value={this.state.deadline}
//                                     onChange={(e) => this.onDeadlineChange(e)}
//                                 />
//                                 <button
//                                     onClick={this.handlePostSubmit}
//                                 >
//                                     create post
//                                 </button>
//                             </form>
//                             {posts.length !== 0 ? (
//                                 posts.map((post) => {
//                                     return (
//                                         <PostPreview
//                                             key={post.id}
//                                             post={post}
//                                         />
//                                     );
//                                 })
//                             ) : (
//                                 <h2>
//                                     There are no posts for this course!
//                                 </h2>
//                             )}
//                         </div>
//                     </div>
//                 )}
//             </div>
//         );
//     }
// }
