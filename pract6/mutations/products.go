package mutations

import (
	"log"

	"pract6/db"
	"pract6/types"

	"math/rand"

	"github.com/graphql-go/graphql"
)

// GetCreateProductMutation is used to create new product instance
// http://localhost:8080/product?query=mutation+_{create(name:"Tequila",info:"Alcohol",price:99){id,name,info,price}}
func GetCreateProductMutation() *graphql.Field {
	return &graphql.Field{
		Type:        types.ProductType,
		Description: "Create new product",
		Args: graphql.FieldConfigArgument{
			"name": &graphql.ArgumentConfig{
				Type: graphql.NewNonNull(graphql.String),
			},
			"info": &graphql.ArgumentConfig{
				Type: graphql.String,
			},
			"price": &graphql.ArgumentConfig{
				Type: graphql.NewNonNull(graphql.Float),
			},
		},
		Resolve: func(params graphql.ResolveParams) (interface{}, error) {
			log.Printf("[mutation] create product\n")
			product := types.Product{
				ID:    int64(rand.Intn(100000)),
				Name:  params.Args["name"].(string),
				Info:  params.Args["info"].(string),
				Price: params.Args["price"].(float64),
			}
			db.Products = append(db.Products, product)
			return product, nil
		},
	}
}

// GetUpdateProductMutation is used to edit product by id
// http://localhost:8080/product?query=mutation+_{update(id:1,price:195){id,name,info,price}}
func GetUpdateProductMutation() *graphql.Field {
	return &graphql.Field{
		Type:        types.ProductType,
		Description: "Update product by id",
		Args: graphql.FieldConfigArgument{
			"id": &graphql.ArgumentConfig{
				Type: graphql.NewNonNull(graphql.Int),
			},
			"name": &graphql.ArgumentConfig{
				Type: graphql.String,
			},
			"info": &graphql.ArgumentConfig{
				Type: graphql.String,
			},
			"price": &graphql.ArgumentConfig{
				Type: graphql.Float,
			},
		},
		Resolve: func(params graphql.ResolveParams) (interface{}, error) {
			id, _ := params.Args["id"].(int)
			name, nameOk := params.Args["name"].(string)
			info, infoOk := params.Args["info"].(string)
			price, priceOk := params.Args["price"].(float64)
			product := types.Product{}
			for i, p := range db.Products {
				if int64(id) == p.ID {
					if nameOk {
						db.Products[i].Name = name
					}
					if infoOk {
						db.Products[i].Info = info
					}
					if priceOk {
						db.Products[i].Price = price
					}
					product = db.Products[i]
					break
				}
			}
			return product, nil
		},
	}
}

// GetDeleteProductMutation is used to delete product by its id
// http://localhost:8080/product?query=mutation+_{delete(id:1){id,name,info,price}}
func GetDeleteProductMutation() *graphql.Field {
	return &graphql.Field{
		Type:        types.ProductType,
		Description: "Delete product by id",
		Args: graphql.FieldConfigArgument{
			"id": &graphql.ArgumentConfig{
				Type: graphql.NewNonNull(graphql.Int),
			},
		},
		Resolve: func(params graphql.ResolveParams) (interface{}, error) {
			id, _ := params.Args["id"].(int)
			product := types.Product{}
			for i, p := range db.Products {
				if int64(id) == p.ID {
					product = db.Products[i]
					db.Products = append(db.Products[:i], db.Products[i+1:]...)
				}
			}
			return product, nil
		},
	}
}
