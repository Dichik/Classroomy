import { React, useEffect, useMemo, useState } from 'react';
import styled from 'styled-components';
import { useTable } from 'react-table';
import Spinner from '../spinner/Spinner';
import './index.css';

const Styles = styled.div`
    padding: 1rem;

    table {
        border-spacing: 0;
        border: 1px solid black;
        width: 1000px;

        tr {
            :last-child {
                td {
                    border-bottom: 0;
                }
            }
        }

        th,
        td {
            margin: 0;
            padding: 0.5rem;
            border-bottom: 1px solid black;
            border-right: 1px solid black;

            :last-child {
                border-right: 0;
            }
        }
    }
`;

function Table({ columns, data }) {
    const { getTableProps, getTableBodyProps, headerGroups, rows, prepareRow } =
        useTable({
            columns,
            data
        });

    return (
        <table {...getTableProps()}>
            <thead>
                {headerGroups.map((headerGroup) => (
                    <tr
                        key="headerGroup.id"
                        {...headerGroup.getHeaderGroupProps()}
                    >
                        {headerGroup.headers.map((column) => (
                            <th key="column.id" {...column.getHeaderProps()}>
                                {column.render('Header')}
                            </th>
                        ))}
                    </tr>
                ))}
            </thead>
            <tbody {...getTableBodyProps()}>
                {rows.map((row) => {
                    prepareRow(row);
                    return (
                        <tr key="row.id" {...row.getRowProps()}>
                            {row.cells.map((cell) => {
                                return (
                                    <td
                                        key="column.id"
                                        {...cell.getCellProps()}
                                    >
                                        {cell.render('Cell')}
                                    </td>
                                );
                            })}
                        </tr>
                    );
                })}
            </tbody>
        </table>
    );
}

export default function Deadline() {
    const [loading, setLoading] = useState(false);
    const [posts, setPosts] = useState([]);

    const columns = useMemo(
        () => [
            {
                Header: 'deadlines',
                columns: [
                    {
                        Header: 'title',
                        accessor: 'title'
                    },
                    {
                        Header: 'date',
                        accessor: 'deadline'
                    }
                ]
            }
        ],
        []
    );

    const getPostsWithNeadDeadlines = () => {
        fetch('http://localhost:8080/posts/deadlines')
            .then((response) => response.json())
            .then((data) => {
                setPosts(data);
                setLoading(false);
            });
    };

    useEffect(() => {
        setLoading(true);
        getPostsWithNeadDeadlines();
    }, []);

    if (loading) {
        return <Spinner />;
    }

    return (
        <div className='deadlines-table'>
            <Styles>
                <Table columns={columns} data={posts} />
            </Styles>
        </div>
    );
}
