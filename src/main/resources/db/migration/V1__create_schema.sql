CREATE TABLE "gods"(
    "id" UUID NOT NULL,
    "name" VARCHAR NOT NULL,
    "purpose" VARCHAR NOT NULL
);
ALTER TABLE "gods" ADD CONSTRAINT "gods_id" PRIMARY KEY("id");