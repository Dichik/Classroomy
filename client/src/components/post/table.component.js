import { React, useMemo } from 'react';
import { useTable } from 'react-table';

export default function Table({ data }) {
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
