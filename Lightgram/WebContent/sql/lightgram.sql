select c.*
from (select rownum r, b.*
		from (select *
				from (select * from show1_post
						union
						select * from show2_post where openLevel < 3
						union
						select * from show3_post where openLevel < 3
						union 
						select * from show4_post
						where hashtag like '%#여기여기%' and openLevel = 1
						union 
						select * from show2_post
						where hashtag like '%#여기여기%' and openLevel = 1
						) a
				order by a.posted_time
			) b
		) c
where c.r < 12;

select e.*
from (select rownum r, d.* , count(*) over(partition by 1)
		from(	select c.* 
				from (select b.profileImg, a.* 
						from ( select * from show1_post
								union 
								select * from show2_post where openlevel < 3
								union 
								select * from show3_post where itemNum = 2 and openLevel = 1
								) a 
							inner join All_users b
							on a.id = b.id
						) c
				order by c.posted_time desc 
			)d
		)e
where e.r<4;

select *
from show4_post
where hashtag like '%#여기여기%' and openLevel = 1;


select c.*
from (select rownum r, b.*
		from (select *
				from show1_post, show2_post
				order by posted_time desc) b
		) c
where c.r<20;
	
select ROW_NUMBER() OVER(ORDER BY show2_post.posted_time), *
from show2_post
order by posted_time;

select * from all_users;
select * from show8_post_replied;

select b.profileImg, a.* 
from show8_post_replied a 
	inner join All_users b 
	on a.reply_id = b.id 
where a.itemNum = 1;