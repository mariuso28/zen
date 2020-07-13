select id,contact,level,parentid,rating,downlinecnt,childrencnt from baseuser 
		where contact in ('mony','yu','duong','aok','yous','suy','taing' ) order by level;

getNextAvailableParent
select id,contact from 
(select id,contact,random() as r from baseuser where parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f' and childrencnt<3 and rating>0 order by downlinecnt,r)
as A limit 1

if not null
	return parent

getPossibleDownlineParents

select id,contact from baseuser where childrencnt=3 and parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f' and rating>0;

if (null)
	return null;

select id,contact from
(select id,contact,random() as r from baseuser where childrencnt < 3 and parentid 
	in (select id from baseuser where childrencnt=3 and parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f' and rating>0) 
	and rating>0 order by downlinecnt,r)
as A limit 1

if not null
	return parent

select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt < 3 
	and parentid in
	(select id from baseuser where rating>0 and childrencnt = 3 
	and parentid in
	(select id from baseuser where rating>0 and childrencnt = 3 
	and parentid in
	(select id from baseuser where rating>0 and childrencnt = 3 and 
	parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f'
	)
	)
	) 
order by downlinecnt,r limit 1

select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt = 3 
	and parentid in
	(select id from baseuser where rating>0 and childrencnt = 3 and 
	parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f'
	)
order by downlinecnt,r limit 1

select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt = 3 
	and parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f'
order by downlinecnt,r limit 1


select id,contact,downlinecnt,random() as r from baseuser 
where rating>0 and childrencnt = 3  
and parentid in 
(select id from baseuser where rating>0 and childrencnt = 3 and parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f') 
order by downlinecnt,r limit 1

select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt = 3  
and parentid in 
(select id from baseuser where rating>0 and childrencnt = 3 and parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f') 
order by downlinecnt,r limit 1

select id,contact,downlinecnt,random() as r from baseuser 
where rating>0 and childrencnt = 3 and parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f' order by downlinecnt,r limit 1

select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt = 3  
and parentid in (select id from baseuser where rating>0 and childrencnt = 3 
and parentid in (select id from baseuser where rating>0 and childrencnt = 3 and 
parentid = '24e6da31-89bc-43c1-a44d-59d0cdae7f4f')) order by downlinecnt,r limit 1


select * from baseuser where rating=0;


select childrencnt from baseuser where id='a65b7010-2712-4317-8325-19647a3f5e6e';

select id,contact,downlinecnt,random() as r from baseuser where rating>0 and childrencnt = 3  and parentid in 
(select id from baseuser where rating>0 and childrencnt = 3 and parentid in 
(select id from baseuser where rating>0 and childrencnt = 3 and parentid = 'b0082c40-9456-4edd-a0d3-9718776545e3')) 
order by downlinecnt,r limit 1






