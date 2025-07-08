select * from lotto;


SELECT a.attname AS primary_key
FROM pg_index i
         JOIN pg_attribute a ON a.attrelid = i.indrelid AND a.attnum = ANY(i.indkey)
WHERE i.indrelid = 'purchase_order_detail'::regclass
  AND i.indisprimary;

SELECT column_name
FROM information_schema.columns
WHERE table_name = 'farmacia';
 alter table farmacia
     add columns   geography(point,4326);



drop table  lotto;

create table lotto(
id text,
farmaco int,
production_date DATE,
elapse d_date DATE,
PRIMARY KEY (id,farmaco)
);


select * from farmaco;
select * from farmaco_all;
select * from lotto
                 inner join farmaco_all on farmaco_all.id=lotto.farmaco


create table purchase_order(
id int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
data Date NOT NULL,
invoice_number int not null,
subtotale double precision not null,
iva double precision not null,
totale double precision not null,
provider_order_id text,
pharma_house text,
invoice_id int
);


create table  purchase_order_detail(
id int PRIMARY KEY  GENERATED ALWAYS AS IDENTITY,
lotto varchar not null,
farmaco int not null,
purchase_order int not null  REFERENCES purchase_order(id),
price double precision not null,
quantity double precision not null,
vat_percent int  not null,
FOREIGN KEY (lotto,farmaco) REFERENCES lotto(id,farmaco)

);

alter table  purchase_order
    add column provider_order_id text,
        add column pharma_house text;

drop table purchase_order;

alter table  purchase_order
alter column pharma_house set not null,
alter column provider_order_id set not null;

select  * from purchase_order;
select * from purchase_order_detail;



delete from purchase_order;

drop  table  purchase_invoice;

create table  purchase_invoice(
id int  PRIMARY KEY  GENERATED ALWAYS AS IDENTITY,
invoice_number text NOT NULL,
issue_date DATE NOT NULL,
payment_type text NOT NULL,
subtotal double precision NOT NULL,
vat_amount double precision NOT NULL,
total double precision NOT NULL,
create_at TIMESTAMP DEFAULT  CURRENT_TIMESTAMP
);
select  * from purchase_invoice;


create table purchase_credit_note
(
    id int PRIMARY KEY generated always as identity,
    credit_note_number text NOT NULL,
    invoice_id int NOT NULL REFERENCES purchase_invoice(id),
    issue_data DATE NOT NULL,
    motivo text NOT NULL ,
    pharma_id  int references pharma(id),
    subtotal double precision not null,
    vat_amount double precision not null,
    total double precision not null,
    created_at TIMESTAMP DEFAULT current_timestamp
);
alter table purchase_credit_note
add constraint different UNIQUE (invoice_id);




create table purchase_credit_note_details(
id int PRIMARY KEY generated always as identity,
credit_note_id int NOT NULL  REFERENCES purchase_credit_note(id),
order_details  int not null references  purchase_order_detail(id),
quantity int not null,
price double precision not null,
nome_farmaco text,
vat_percent double precision
);





alter table   purchase_invoice
add column
purchase_order_id int references  purchase_order(id);

select * from lotto;

select  * from lotto
                  inner join farmaco_all on farmaco_all.id=lotto.farmaco;

select  * from lotto
 inner join farmaco_all on farmaco_all.id=lotto.farmaco where farmaco_all.casa_farmaceutica='Angelini';

SELECT anagrafica_cliente FROM pharma;

update purchase_order set provider_order_id='1401' where id=16;
update purchase_order set provider_order_id='1201' where id=17;
update purchase_order set provider_order_id='1901' where id=18;


select * from purchase_order_detail
inner join farmaco_all on purchase_order_detail.farmaco=farmaco_all.id
select * from purchase_order_detail
                    inner join farmaco_all on purchase_order_detail.farmaco=farmaco_all.id
                     where purchase_order=15;

delete
from purchase_order
where id=19


update purchase_order
set invoice_id= ? where id=?

select * from purchase_order where purchase_order.invoice_id IS NULL;
select  * from purchase_order;
update  purchase_order
set provider_order_id='p100'
where id=28


select pharma_house from purchase_invoice
inner join purchase_order po on purchase_invoice.id = po.invoice_id
where invoice_id=6
group by  pharma_house;

select  * from pharma;
select  * from lotto
                     inner join farmaco_all on farmaco_all.id=lotto.farmaco

select * from farmaco_all

SELECT table_schema AS schema_name,
       table_name AS view_name,
       view_definition
FROM information_schema.views
ORDER BY schema_name, view_name;

CREATE OR REPLACE VIEW farmaco_all as
 SELECT farmaco.id,
    farmaco.nome,
       farmaco.descrizione,
       categoria.nome AS categoria,
       tipologia.nome AS tipologia,
        concat(m.quantity, m.unit) AS misura,
    pa.nome AS principio_attivo,
       p.id as pharma_id,
       p.anagrafica_cliente AS casa_farmaceutica

FROM (((((farmaco
    JOIN categoria ON ((farmaco.categoria = categoria.id)))
    JOIN tipologia ON ((farmaco.tipologia = tipologia.id)))
    JOIN misura m ON ((m.id = farmaco.misura)))
    JOIN principio_attivo pa ON ((pa.id = farmaco.principio_attivo)))
    JOIN pharma p ON ((farmaco.casa_farmaceutica = p.id)));


select  * from misura;
CREATE OR REPLACE VIEW farmaco_all as
SELECT farmaco.id,
       farmaco.nome,
       farmaco.descrizione,
       categoria.nome AS categoria,
       tipologia.nome AS tipologia,
       concat(m.misure, m.unit) AS misura,
       pa.nome AS principio_attivo,
       p.anagrafica_cliente AS casa_farmaceutica,
       farmaco.qty,
       p.id as pharma_id
FROM (((((farmaco
    JOIN categoria ON ((farmaco.categoria = categoria.id)))
    JOIN tipologia ON ((farmaco.tipologia = tipologia.id)))
    JOIN misura m ON ((m.id = farmaco.misura)))
    JOIN principio_attivo pa ON ((pa.id = farmaco.principio_attivo)))
    JOIN pharma p ON ((farmaco.casa_farmaceutica = p.id)));

 DROP VIEW  IF EXISTS farmaco_all;

 select * from pharma;


select  * from lotto
inner join farmaco_all on farmaco_all.id=lotto.farmaco
where pharma_id=1;


select * from purchase_order;

alter table purchase_order
add column pharma_id int REFERENCES  pharma(id)

select * from pharma;

update purchase_order
set pharma_id=3
where id =29;


select * from pharma;
select * from purchase_credit_note;

select   purchase_order.id,purchase_order.data,purchase_order.subtotale,
         purchase_order.iva,purchase_order.totale, purchase_order.provider_order_id,purchase_order.pharma_id,pharma.anagrafica_cliente
         from purchase_order
inner join pharma on  pharma.id=purchase_order.pharma_id
where purchase_order.invoice_id IS NULL;



select * from purchase_order_detail;


alter table purchase_order_detail
add column  nome_farmaco text;
select   purchase_order.id,purchase_order.data,purchase_order.subtotale,
                 purchase_order.iva,purchase_order.totale, purchase_order.provider_order_id,purchase_order.pharma_id,pharma.anagrafica_cliente from
               purchase_order
                 inner join pharma on  pharma.id=purchase_order.pharma_id
                 where purchase_order.invoice_id IS NULL;

select * from purchase_invoice;
delete  from purchase_invoice;

alter table purchase_invoice
add column pharma_id int references pharma(id);
update purchase_invoice  set pharma_id=1
where id=6

select  * from lotto
                     inner join farmaco_all on farmaco_all.id=lotto.farmaco where pharma_id = 1

select  * from lotto
                     inner join farmaco_all on farmaco_all.id=lotto.farmaco where pharma_id = 1


select purchase_order  purchase_order.id,purchase_order.data,purchase_order.subtotale,
             purchase_order.iva,purchase_order.totale, purchase_order.provider_order_id,purchase_order.pharma_id,pharma.anagrafica_cliente from
             purchase_order
                inner join pharma on  pharma.id=purchase_order.pharma_id
                 where purchase_order.invoice_id IS NULL
                and purchase_order.pharma_id = 1


delete from purchase_order;




select * from misura;

alter table misura;
    update misura set quantity=28
        where id=3;
add column  quantity int;


select * from misura;
update misura set misure=150 where id=13;


alter table  misura
add constraint unit_miusure_check UNIQUE (misure,unit);


select * from misura;

alter table  misura
drop column quantity;

select  * from farmaco;
alter table farmaco
add column  qty int default 0 not null  ;

update  farmaco
set qty=20
where id=59;

select *
from farmaco_all;

select * from lotto
                 inner join farmaco_all on farmaco_all.id=Lotto.farmaco
select * from farmaco_all;


select * from lotto;
delete from lotto where id IN ('m100','m7200');

select  * from lotto
                    inner join farmaco_all on farmaco_all.id=lotto.farmaco;

delete from lotto where lotto.id IN ('m100','m7200','m378','m7100','UP223','m229','m7aa')


select count(*) from purchase_credit_note where invoice_id=1;

select * from purchase_credit_note
          inner join purchase_credit_note_details on purchase_credit_note.id = purchase_credit_note_details.credit_note_id
         where invoice_id=8;


            select * from purchase_credit_note
                          inner join purchase_credit_note_details on purchase_credit_note.id = purchase_credit_note_details.credit_note_id
                     inner join purchase_order_detail on purchase_order_detail.id= purchase_credit_note_details.order_details
                     inner join farmaco_all on purchase_order_detail.farmaco=farmaco_all.id
                             where invoice_id= 8;

select  * from farmaco_all;

CREATE OR REPLACE PROCEDURE update_all_farmaco_names()
    LANGUAGE plpgsql
AS $$
BEGIN
    -- Update all rows in purchase_order_detail using farmaco_all view
    UPDATE purchase_order_detail pod
    SET nome_farmaco = fa.nome
    FROM farmaco_all fa
    WHERE pod.farmaco = fa.id;

    -- Print how many rows were updated
    RAISE NOTICE '% rows updated in purchase_order_detail.', FOUND;
END;
$$;

CALL update_all_farmaco_names();


select *
from lotto;





create table  farmacia(
id int generated always as identity,
ragione_sociale text not null,
p_iva text not null unique,
street text not null,
cap int not null,
comune text not null,
province text not null

);


select * from lotto
                 inner join farmaco_all on farmaco_all.id=Lotto.farmaco;


select avg( lotto.elapsed_date-current_date) as remaining
       from lotto
                inner join farmaco_all on farmaco_all.id=Lotto.farmaco where  farmaco=60

select * from farmaco_all;


select * from farmacia


create





